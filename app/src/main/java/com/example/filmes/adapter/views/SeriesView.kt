package com.example.filmes.adapter.views

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.serie.Serie
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity
import com.facebook.shimmer.ShimmerFrameLayout

class SeriesView (viewGroup: ViewGroup) : BaseViewHolder<Serie>(
    R.layout.movie_an_series_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Serie) {
        itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.name
        if ( item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(
            R.drawable.logo
        )
        else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load("https://image.tmdb.org/t/p/w500" + item.poster_path)
        itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerMoviesAndSeriesCell).visibility = View.GONE
        itemView.findViewById<TextView>(R.id.nomeOrTitle).visibility = View.VISIBLE
        itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).visibility = View.VISIBLE
        itemView.findViewById<LinearLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
            val intent = Intent( context, SerieDetailsActivity::class.java ).apply {
                putExtra("id", item.id.toString() )
            }
            context.startActivity(intent)
        }
    }
}

