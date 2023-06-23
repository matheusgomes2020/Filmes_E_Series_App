package com.example.filmes.ui.movieDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmes.adapter.MovieAdapter
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")

        observeMovies()

        viewModel.getMovieInfo(id!!)

    }

    fun observeMovies() {

        try {
            viewModel.movieInfo.observe(this) {
                Log.d("RESGATE", "observeMovies: " + it.toString())

                binding.movieOverview.text = it.overview
                binding.movieTitle.text = it.title
                binding.imageView2.load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                binding.textData.text = it.release_date
                binding.textDuration.text = it.runtime.toString()

                var gen = ""
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }

                binding.textGenres.text = gen

                when ( it.vote_average ) {

                    in 0.0..1.9 -> binding.texRating.text = "🌟⭐⭐⭐⭐"
                    in 2.0..3.9 -> binding.texRating.text = "🌟🌟⭐⭐⭐"
                    in 4.0..5.9 -> binding.texRating.text = "🌟🌟🌟⭐⭐"
                    in 6.0..7.9 -> binding.texRating.text = "🌟🌟🌟🌟⭐"
                    in 8.0..10.0 -> binding.texRating.text = "🌟🌟🌟🌟🌟"

                }


            }
            }catch (e: Exception){
            e.printStackTrace()
        }

        }

}










