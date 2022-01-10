package com.projek.submissionjetpack1.ui.favouritefilm.FavouriteTvShow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.databinding.ItemsTvShowBinding
import com.projek.submissionjetpack1.ui.tvShow.TvShowAdapter
import com.projek.submissionjetpack1.ui.tvShow.TvShowCallback

class FavTvAdapter(private val tvShowCallback: TvShowCallback): RecyclerView.Adapter<FavTvAdapter.TvShowViewHolder>() {
    private val listTvShows=ArrayList<FilmEntity>()
    fun setListTvShow(listTvShow:ArrayList<FilmEntity>?){
        if(listTvShow==null) return
        this.listTvShows.clear()
        this.listTvShows.addAll(listTvShow)
    }

    inner class TvShowViewHolder(private val binding: ItemsTvShowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(filmEntity: FilmEntity) {
            with(binding){
                var isiGenre = filmEntity.genres.get(0)
                var isInt = isiGenre.code
                var hasil = isInt in 48..57
                if(!hasil) {
                    tvGenre.visibility = View.VISIBLE
                    tvGenre.text = filmEntity.genres
                }
                titleMovie.text=filmEntity.title
                tvReleaseDate.text=itemView.context.resources.getString(R.string.release_date,"\n${filmEntity.releaseDate}")
                Glide.with(itemView.context)
                    .load(filmEntity.imageMovie)
                    .apply{
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.error)
                    }
                    .into(ivTvShow)

                imgShare.setOnClickListener{
                    tvShowCallback.shareTvShow(filmEntity)
                }
                cvTvShow.setOnClickListener{
                    tvShowCallback.navigateToDetail(filmEntity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        var binding= ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(listTvShows[position])
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }
}