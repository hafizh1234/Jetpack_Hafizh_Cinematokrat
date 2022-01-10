package com.projek.submissionjetpack1.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.databinding.FragmentMoviesBinding
import com.projek.submissionjetpack1.ui.detailFilm.DetailFilmActivities
import com.projek.submissionjetpack1.viewmodel.ViewModelFactory
import com.projek.submissionjetpack1.vo.StatusProcced

class MoviesFragment : Fragment(), MovieCallback {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[MovieViewModel::class.java]
            movieAdapter = MovieAdapter(this)
            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = movieAdapter
            }

            viewModel.getMovie().observe(viewLifecycleOwner, { tvShowList ->
                if (tvShowList != null) {
                    when (tvShowList.status) {
                        StatusProcced.LOADING -> {
                            binding.progressbarMovie.visibility = View.VISIBLE
                        }
                        StatusProcced.ERROR -> {
                            binding?.progressbarMovie.visibility = View.GONE
                            Toast.makeText(
                                requireActivity(),
                                "Terjadi Kesalahan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        StatusProcced.SUCCESS -> {
                            var tvShows = ArrayList<FilmEntity>()
                            tvShowList.data?.let { tvShows.addAll(it as ArrayList<FilmEntity>) }
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
                    }
                }
            })
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater)
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