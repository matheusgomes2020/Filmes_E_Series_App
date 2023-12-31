package com.example.filmes.adapter.views

import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.serie.Serie
import com.example.filmes.ui.seriesDetails.SerieDetailsActivity

class SearchSeriesView (viewGroup: ViewGroup) : BaseViewHolder<Serie>(
    R.layout.search_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Serie) {
        itemView.findViewById<TextView>(R.id.textNameEpisode).text = item.name
        itemView.findViewById<TextView>(R.id.textOverviewEpisode).text = item.overview
        var rating = ""
        when ( item.vote_average ) {
            in 0.0..1.9 -> rating = "⭐"
            in 2.0..3.9 -> rating = "⭐⭐"
            in 4.0..5.9 -> rating = "⭐⭐⭐"
            in 6.0..7.9 -> rating = "⭐⭐⭐⭐"
            in 8.0..10.0 -> rating = "⭐⭐⭐⭐⭐"
        }
        itemView.findViewById<TextView>(R.id.ratingSearchCell).text = rating
        if ( !item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageEpisode).load( "https://image.tmdb.org/t/p/w500" + item.poster_path)
        else itemView.findViewById<ImageView>(R.id.imageEpisode).load(R.drawable.logo)
        itemView.findViewById<LinearLayout>(R.id.movie_search_cellContainer).setOnClickListener {
            val intent = Intent( context, SerieDetailsActivity::class.java ).apply {
                putExtra("id", item.id.toString() )
            }
            context.startActivity(intent)
        }
    }
}