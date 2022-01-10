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
import com.projek.submissionjetpack1.databinding.FragmentFavShowBinding
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow.FavouriteViewModel
import com.projek.submissionjetpack1.ui.detailFilm.DetailFilmActivities
import com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow.FavTvAdapter
import com.projek.submissionjetpack1.ui.tvShow.TvShowAdapter
import com.projek.submissionjetpack1.ui.tvShow.TvShowCallback
import com.projek.submissionjetpack1.viewmodel.ViewModelFactory

class FavShowFragment : Fragment(), TvShowCallback {
    private var _binding: FragmentFavShowBinding? = null
    private val binding get() = _binding!!
    private lateinit var showFavAdapter:FavTvAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[FavouriteViewModel::class.java]
            showFavAdapter = FavTvAdapter(this)
            with(binding.rvTvShow) {
                layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = showFavAdapter
            }

            viewModel.getTvShows().observe(viewLifecycleOwner, { tvShowList ->
                if (tvShowList != null) {
                    var tvShows = ArrayList<FilmEntity>()
                    tvShowList.let { tvShows.addAll(it as ArrayList<FilmEntity>) }
                    var tvShowsResult = ArrayList<FilmEntity>()
                    for (item in tvShows) {
                        if (item.filmType == "TvShow") {
                            tvShowsResult.add(item)
                        }
                    }
                    binding.progressbarTvShow.visibility = View.GONE

                    showFavAdapter.setListTvShow(tvShowsResult)
                    showFavAdapter.notifyDataSetChanged()
                }

            })
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavShowBinding.inflate(inflater)
        return binding.root
    }

    override fun shareTvShow(filmEntity: FilmEntity) {
        var shareCompat = ShareCompat.IntentBuilder(requireActivity())
        var mimeType = "text/plain"
        shareCompat.setChooserTitle("Bagikan informasi film")
        shareCompat.setType(mimeType)
        shareCompat.setText(resources.getString(R.string.text_share, filmEntity.title))
        shareCompat.startChooser()
    }

    override fun navigateToDetail(filmEntity: FilmEntity) {
        var intent = Intent(requireActivity(), DetailFilmActivities::class.java)
        intent.putExtra(DetailFilmActivities.ExtraFilmId, filmEntity.idFilm)
        intent.putExtra(DetailFilmActivities.FilmType, filmEntity.filmType)
        startActivity(intent)
    }
}