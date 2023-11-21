import React, { useEffect, useState } from 'react'
import { View ,StyleSheet,ScrollView} from 'react-native'
import { Header1 } from '../Header/Header1'
import fetchAnimeData from '../../Utils/fetchAnimeData'
import { FetchAnimeList } from '../ListAnimeMenu/FetchAnimeList'
export const Home = () => {
  const [animeData, setAnimeData] = useState([]);
  const [animeData2, setAnimeData2] = useState([]);
  const [animeData3, setAnimeData3] = useState([]);
  const [animeData4, setAnimeData4] = useState([]);
  // fetchAnimeData es una función que se encuentra en la carpeta Utils
  // y que se encarga de hacer la petición a la API de AniList
  useEffect(() => {
    fetchAnimeData('FALL',2023,'TRENDING_DESC') // Puedes cambiar el parámetro según tu necesidad
      .then(data => {
        setAnimeData2(data);
      })
      .catch(error => {
        console.error(error);
        setAnimeData2([]);
      });
  }, []);

  useEffect(() => {
    fetchAnimeData('FALL',2023,'POPULARITY_DESC') // Puedes cambiar el parámetro según tu necesidad
      .then(data => {
        setAnimeData(data);
      })
      .catch(error => {
        console.error(error);
        setAnimeData([]);
      });
  }, []);
  useEffect(() => {
    fetchAnimeData('WINTER',2024,'POPULARITY_DESC') // Puedes cambiar el parámetro según tu necesidad
      .then(data => {
        setAnimeData3(data);
      })
      .catch(error => {
        console.error(error);
        setAnimeData3([]);
      });
  }, []);
  useEffect(() => {
    fetchAnimeData(null,null,'POPULARITY_DESC') // Puedes cambiar el parámetro según tu necesidad
      .then(data => {
        setAnimeData4(data);
      })
      .catch(error => {
        console.error(error);
        setAnimeData4([]);
      });
  }, []);
  return (
    <ScrollView contentContainerStyle={styles.scrollViewContainer}>
      <View style={styles.container}>
        <FetchAnimeList animeData={animeData2} title="TRENDING NOW" />
        <FetchAnimeList animeData={animeData} title="POPULAR THIS SEASON" />
        <FetchAnimeList animeData={animeData3} title="UPCOMING NEXT SEASON" />
        <FetchAnimeList animeData={animeData4} title="ALL TIME POPULAR" />
      </View>
    </ScrollView>
  )
}

const styles = StyleSheet.create({
  scrollViewContainer: {
    flexGrow: 1,
  },
  container:{
    flex:1,
    paddingHorizontal:16, 
    backgroundColor: 'rgb(11, 22, 34)',
    }



})