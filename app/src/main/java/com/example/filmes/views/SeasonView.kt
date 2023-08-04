package com.example.filmes.views

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.filmes.R
import com.example.filmes.adapter.BaseViewHolder
import com.example.filmes.model.serie.Season
import com.example.filmes.ui.season.SeasonEpisodes

class SeasonView (viewGroup: ViewGroup, private var seriesId: String, private var fragmentManager: FragmentManager) : BaseViewHolder<Season>(
    R.layout.movie_an_series_cell,
    viewGroup
) {

    private val context = viewGroup.context

    override fun bind(item: Season) {
        itemView.findViewById<TextView>(R.id.nomeOrTitle).text = item.season_number.toString() + " - temporada"
        if ( !item.poster_path.isNullOrEmpty() ) itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load( "https://image.tmdb.org/t/p/w500" + item.poster_path )
        else itemView.findViewById<ImageView>(R.id.imageViewMovieAndSeries).load(R.drawable.logo)
        itemView.findViewById<ConstraintLayout>(R.id.movieAndSeriesCellContainer).setOnClickListener {
            SeasonEpisodes( item, seriesId ).show( fragmentManager, "seasonTag" )
        }
    }
}
