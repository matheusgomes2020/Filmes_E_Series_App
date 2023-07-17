package com.example.filmes.ui.movieDetails

import android.R
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.adapter.cast.CastAdapter
import com.example.filmes.adapter.cast.CastClickListener
import com.example.filmes.adapter.movie.MovieAdapter
import com.example.filmes.adapter.movie.MovieClickListener
import com.example.filmes.databinding.ActivityMovieDetailsBinding
import com.example.filmes.model.CastX
import com.example.filmes.model.Movie
import com.example.filmes.model.Serie
import com.example.filmes.views.CastView
import com.example.filmes.views.MovieView
import com.example.filmes.views.SeriesView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val id = intent.getStringExtra("id")
        viewModel.getMovieInfo( id!! )
        viewModel.getCast( id!! )
        observeMovies()
        observeCast()
    }

    private fun observeMovies() {

        try {
            var roteiro = ""
            var gen = ""

            viewModel.movieInfo.observe(this) {
                binding.movieOverview.text = it.overview
                binding.movieTitle.text = it.title
                binding.textData.text = it.release_date
                binding.textDuration.text = it.runtime.toString()
                binding.textViewDirecaoFilme.text = it.runtime.toString()
                for (i in it.credits.crew) if ( i.job == "Director" ) binding.textViewDirecaoFilme.text = i.name
                for (i in it.credits.crew) if ( i.department == "Writing" ) roteiro += i.name + "\n"
                binding.textViewRoteiro.text = roteiro
                it.genres.forEachIndexed { index, genres ->
                    gen += genres.name + "  "
                }
                binding.textGenres.text = gen
                when ( it.vote_average ) {
                    in 0.0..1.9 -> binding.texRating.text = "⭐"
                    in 2.0..3.9 -> binding.texRating.text = "⭐⭐"
                    in 4.0..5.9 -> binding.texRating.text = "⭐⭐⭐"
                    in 6.0..7.9 -> binding.texRating.text = "⭐⭐⭐⭐"
                    in 8.0..10.0 -> binding.texRating.text = "⭐⭐⭐⭐⭐"
                }

                if ( it.videos.results.isNullOrEmpty() ) {
                    binding.videoView.visibility = View.GONE
                } else {
                    binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = it.videos.results[0].key
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }

                setRecyclerViewSimilar(it.similar.results)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun observeCast() {

        try {
            viewModel.cast.observe(this) {
                setRecyclerViewCast( it )
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun setRecyclerViewCast(list: List<CastX>) {

        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewSimilar(list: List<Movie>) {

        binding.recyclerMovieSimilars.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                MovieView(it)
            }.apply {
                items = list.toMutableList()
            }
        }
    }
}
