import React from 'react'
import './SearchResults.css'
import { Result } from './Result'

export const SearchResults = ({ results,clearSearch}) => {
  return (
    <div className='result-list'>
        {results.map((result,id) => {
            return <Result result={result} id={id} clearSearch = {clearSearch}/>
        })}
    </div>
  )
}
