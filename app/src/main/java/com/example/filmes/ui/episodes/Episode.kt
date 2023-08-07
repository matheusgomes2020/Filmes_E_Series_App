package com.example.filmes.ui.episodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmes.databinding.FragmentEpisodeBinding
import com.example.filmes.model.general.Cast
import com.example.filmes.model.general.Image
import com.example.filmes.model.general.Images
import com.example.filmes.model.person.Profile
import com.example.filmes.adapter.views.CastView
import com.example.filmes.adapter.views.EpidoseImagesView
import com.example.filmes.adapter.views.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class Episode(private var seriesId: String, private var seasonNumber: Int, private var episodeNumber: Int ): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private val  episodeViewModel: EpisodeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeViewModel.getEpisodeInfo( seriesId, seasonNumber, episodeNumber )
        observe()
    }

    private fun observe(){

        try {
            var roteiro = ""
            episodeViewModel.episodeInfo.observe(viewLifecycleOwner) {
                if (!it.name.isNullOrEmpty()) binding.textView10.text = it.name else binding.textView10.visibility = View.GONE
                if (!it.overview.isNullOrEmpty()) binding.textView11.text = it.overview else binding.textView11.visibility = View.GONE
                var director = ""
                var writing = ""
                for (i in it.crew) if ( i.job == "Director" ) {binding.textViewDirecaoEdisodio.text = i.name
                director = i.name }
                for (i in it.crew) if ( i.department == "Writing" ) { roteiro += i.name + "\n"
                    writing = i.name }
                binding.textViewRoteiroEdisodio.text = roteiro
                if (director.isNullOrEmpty()) {binding.textViewDirecaoEdisodio.visibility = View.GONE
                binding.textView8.visibility = View.GONE}
                if (writing.isNullOrEmpty()) {binding.textViewRoteiroEdisodio.visibility = View.GONE
                    binding.textView9.visibility = View.GONE}
                if ( !it.guest_stars.isNullOrEmpty() ) setRecyclerViewCast(it.guest_stars)
                else binding.linearLayout2.visibility = View.GONE
                if ( it.images.stills.isNullOrEmpty() ) binding.recyclerEpisodeImages.visibility = View.GONE
                else setRecyclerViewImages( it.images.stills!! )

            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun setRecyclerViewCast(list: List<Cast>) {

        binding.recyclerMoviecast.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                CastView(it, childFragmentManager )
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    private fun setRecyclerViewImages(list: List<Profile>) {

        binding.recyclerEpisodeImages.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = com.example.filmes.adapter.Adapter {
                EpidoseImagesView(it, childFragmentManager)
            }.apply {
                items = list.toMutableList()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }
}