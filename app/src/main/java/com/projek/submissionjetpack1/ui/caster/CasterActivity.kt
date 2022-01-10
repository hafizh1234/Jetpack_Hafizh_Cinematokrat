package com.projek.submissionjetpack1.ui.caster

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.databinding.ActivityCasterBinding
import com.projek.submissionjetpack1.viewmodel.ViewModelFactory
import com.projek.submissionjetpack1.vo.StatusProcced

class CasterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCasterBinding

    //ambil extra_film
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(
            this,
            factory
        )[CasterViewModel::class.java]

        var extra = intent.extras
        var actorChosen: Casting
        if (extra != null) {
            var typeCaster = extra.getString(ExtraType)
            var actorId = extra.getString(ExtraCasterId)
            var filmId = extra.getString(ExtraFilm)
            var info = viewModel.getInfo()
            //getting actor /director that have been chosen by user on the DetailFilmActivities
            if (info[0] == null || info[0] == "" || info[1] == "") {
                if (actorId != null && typeCaster != null && filmId != null) {
                    viewModel.initialize(actorId, filmId)
                }
            }
        }
        viewModel.getCaster().observe(this, { caster ->
            when (caster.status) {
                StatusProcced.ERROR -> {
                    binding.progressbar.visibility= View.GONE
                    Toast.makeText(this, "Terjadi Keslahan", Toast.LENGTH_SHORT).show()
                }
                StatusProcced.SUCCESS -> {
                    binding.progressbar.visibility=View.GONE
                    caster.data?.let { fillCaster(it) }
                }
                StatusProcced.LOADING->{
                    binding.progressbar.visibility=View.VISIBLE
                }
            }
        })

    }


    private fun fillCaster(actor: Casting) {
        with(binding) {
            casterName.text = actor.nameCaster
            dateAndPlaceOfBirth.text = "${actor.placeOfBirth}\n${actor.dateOfBirth}"
            casting.text = actor.typeCast
            if (actor.imageCaster != "") {
                Glide.with(this@CasterActivity)
                    .load(actor.imageCaster)
                    .apply {
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.error)
                    }
                    .into(imageView)
            }
            biographyCaster.text = actor.biographyCaster
        }
    }

    companion object {
        var ExtraType = "extra_type"
        var ExtraCasterId = "extra_actor"
        var ExtraFilm = "extra_film"
    }
}