package com.projek.submissionjetpack1.ui.detailFilm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.databinding.ActivityDetailFilmActivitiesBinding
import com.projek.submissionjetpack1.databinding.ContentDetailFilmBinding
import com.projek.submissionjetpack1.ui.caster.CasterActivity
import com.projek.submissionjetpack1.viewmodel.ViewModelFactory
import com.projek.submissionjetpack1.vo.StatusProcced

class DetailFilmActivities : AppCompatActivity(), ActorCallback {
    companion object {
        var ExtraFilmId = "extra_film"
        var FilmType = "film_type"
    }

    private lateinit var actorDirected: ArrayList<Casting>
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var bindingContent: ContentDetailFilmBinding
    private lateinit var binding:ActivityDetailFilmActivitiesBinding
    private lateinit var viewModel: DetailFilmViewModel
    private var menu:Menu?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindingContent = binding.contentDetail
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        genreAdapter = GenreAdapter()
        actorDirected = arrayListOf()
        actorAdapter = ActorAdapter(this)
        val factory=ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailFilmViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val idFilm: String? = extras.getString(ExtraFilmId)
            val filmType: String? = extras.getString(FilmType)
            val info = viewModel.getCurrentInfo()
            if (info[0] == "" || info[1] == "") {
                if (idFilm != null && filmType != null) {
                    viewModel.initialize(idFilm, filmType)
                }
            }
            with(bindingContent) {
                with(rvActor) {
                    layoutManager = LinearLayoutManager(this@DetailFilmActivities)
                    setHasFixedSize(false)
                    adapter = actorAdapter
                }
                with(rvGenre) {
                    layoutManager = LinearLayoutManager(this@DetailFilmActivities)
                    setHasFixedSize(false)
                    adapter = genreAdapter
                }
            }
            if (idFilm != null) {
                //getting film detail is just simple as calling a film that have choosen by the user from MainActivity
                viewModel.films.observe(this,{filmActors->
                    if(filmActors!=null) {
                        when (filmActors.status) {
                            StatusProcced.LOADING->bindingContent.progressbar.visibility= View.VISIBLE
                            StatusProcced.SUCCESS->{
                                bindingContent.progressbar.visibility=View.GONE

                                filmActors.data?.mCasterEntity?.let {
                                    actorDirected.addAll(it as ArrayList<Casting>)
                                    actorAdapter.setActor(actorDirected)
                                    actorAdapter.notifyDataSetChanged()
                                }
                                filmActors.data?.mFilmEntity?.let{
                                    val genres = it.genres.split(",")
                                    genreAdapter.setListGenre(genres)
                                    genreAdapter.notifyDataSetChanged()
                                    populateFilm(it)
                                }

                            }
                           StatusProcced.ERROR->{
                               bindingContent.progressbar.visibility=View.GONE
                           }
                        }
                    }
                })
            }
        }
    }

    private fun populateFilm(filmChosen: FilmEntity) {
        with(bindingContent) {
            titleFilm.text = filmChosen.title
            ratingFilm.text = filmChosen.rating
            releaseDate.text = filmChosen.releaseDate
            description.text = filmChosen.description
            Glide.with(this@DetailFilmActivities)
                .load(filmChosen.imageMovie)
                .apply {
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.error)
                }
                .into(imageView)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu=menu
        viewModel.films.observe(this, { courseWithModule ->
            if (courseWithModule != null) {
                when (courseWithModule.status) {
                    StatusProcced.LOADING -> bindingContent.progressbar.visibility = View.VISIBLE
                    StatusProcced.SUCCESS -> if (courseWithModule.data != null) {
                        bindingContent.progressbar.visibility = View.GONE
                        val state = courseWithModule.data.mFilmEntity.favourited
                        setBookmarkState(state)
                    }
                    StatusProcced.ERROR -> {
                        bindingContent.progressbar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            viewModel.setFavouriteFilm()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favouritd)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favourite)
        }
    }
    override fun castingClicked(caster: Casting) {
        var intent = Intent(this, CasterActivity::class.java)
        intent.putExtra(CasterActivity.ExtraType, caster.typeCast)
        intent.putExtra(CasterActivity.ExtraCasterId, caster.idCaster)
        intent.putExtra(CasterActivity.ExtraFilm,caster.idFilm)
        startActivity(intent)
    }
}