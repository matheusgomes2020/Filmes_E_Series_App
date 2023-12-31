package com.example.filmes.repository

import com.example.filmes.data.MovieDao
import com.example.filmes.data.SerieDao
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.model.SeriesRoom
import javax.inject.Inject

class RoomRepository @Inject constructor(private val movieDatabaseDao: MovieDao, private val seriesDao: SerieDao ) {

    suspend fun addMovie( movie: MovieRoom ) =  movieDatabaseDao.insert( movie )

    suspend fun getAllMovies(): List<MovieRoom> =  movieDatabaseDao.getMovies()

    suspend fun updateMovie( movie: MovieRoom ) =  movieDatabaseDao.update( movie )

    suspend fun deleteMovie( movie: MovieRoom ) =  movieDatabaseDao.deleteMovie( movie )

    suspend fun deleteAllMovies() =  movieDatabaseDao.deleteAll()

    suspend fun addSeries( series: SeriesRoom ) =  seriesDao.insert( series )

    suspend fun getAllSeries(): List<SeriesRoom> =  seriesDao.getSeries()

    suspend fun updateSeries( series: SeriesRoom ) =  seriesDao.update( series )

    suspend fun deleteSeries( series: SeriesRoom ) =  seriesDao.deleteSeries( series )

    suspend fun deleteAllSeries() =  seriesDao.deleteAll()

}