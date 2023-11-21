import React, { useState, useEffect } from 'react';

const fetchAnimeData  = async (season, seasonYear, sort) => {
  // API URL
    const apiUrl = 'https://graphql.anilist.co';
  // Lógica para construir la consulta basada en si seasonYear se proporciona
  let queryText = `
    query {
      Page {
        media( type: ANIME, sort: ${sort}, isAdult: false) {
          id
          title {
            romaji
          }
          coverImage {
            large
          }
          bannerImage
          description
          averageScore
          genres
          trailer{
            id
            }
        }
      }
    }
  `;

  if (seasonYear) {
    queryText = `
      query {
        Page {
          media(seasonYear: ${seasonYear}, type: ANIME, sort: ${sort}, isAdult: false) {
            id
            title {
              romaji
            }
            coverImage {
              large
            }
            bannerImage
            description
            averageScore
            genres
          }
          trailer{
            id
            }
        }
      }
    `;
  }
  if (season && seasonYear) {
    queryText = `
      query {
        Page {
          media(season: ${season},seasonYear: ${seasonYear}, type: ANIME, sort: ${sort}, isAdult: false) {
            id
            title {
              romaji
            }
            coverImage {
              large
            }
            bannerImage
            description
            averageScore
            genres
            trailer{
              id
              }
          }
        }
      }
    `;
  }
  // Lógica para construir la consulta basada en si algunos parametros se mandan o no
    try {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ query: queryText }),
    });
  
      const data = await response.json();
      return data.data.Page.media;
    } catch (error) {
      console.error('Error fetching data:', error);
      throw new Error('Failed to fetch data');
    }
  };
  
  export default fetchAnimeData ;
  