package com.projek.submissionjetpack1.ui.favouritefilm.FavouriteMovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.databinding.FragmentFavouriteMovieBinding
import com.projek.submissionjetpack1.ui.detailFilm.DetailFilmActivities
import com.projek.submissionjetpack1.ui.movies.MovieAdapter
import com.projek.submissionjetpack1.ui.movies.MovieCallback
import com.projek.submissionjetpack1.viewmodel.ViewModelFactory

class FavouriteMovieFragment : Fragment(), MovieCallback {
    private var _binding: FragmentFavouriteMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[FavouriteMovieViewModel::class.java]
            movieAdapter = MovieAdapter(this)
            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = movieAdapter
            }

            viewModel.getMovieFavourite().observe(viewLifecycleOwner, { tvShowList ->
                var tvShows = ArrayList<FilmEntity>()
                tvShowList.let { tvShows.addAll(it as ArrayList<FilmEntity>) }
                var tvShowsResult = ArrayList<FilmEntity>()
                for (item in tvShows) {
                    if (item.filmType == "Movie") {
                        tvShowsResult.add(item)
                    }
                }
                binding.progressbarMovie.visibility = View.GONE
                movieAdapter.setListMovie(tvShowsResult)
                movieAdapter.notifyDataSetChanged()
            }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun movieShare(filmEntity: FilmEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            val shareCompat = ShareCompat.IntentBuilder(requireActivity())
            shareCompat.setChooserTitle("Bagikan informasi film")
            shareCompat.setType(mimeType)
            shareCompat.setText(resources.getString(R.string.text_share, filmEntity.title))
            shareCompat.startChooser()
        }
    }

    override fun navigateToDetail(filmEntity: FilmEntity) {
        var intent = Intent(requireActivity(), DetailFilmActivities::class.java)
        intent.putExtra(DetailFilmActivities.ExtraFilmId, filmEntity.idFilm)
        intent.putExtra(DetailFilmActivities.FilmType, filmEntity.filmType)
        startActivity(intent)
    }

}