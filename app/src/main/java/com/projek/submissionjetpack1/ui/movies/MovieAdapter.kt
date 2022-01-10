package com.projek.submissionjetpack1.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.databinding.ItemsMovieBinding

class MovieAdapter(private val movieCallback:MovieCallback): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val listMovies=ArrayList<FilmEntity>()
    fun setListMovie(listMovie:ArrayList<FilmEntity>?){
        if(listMovie==null) return
        this.listMovies.clear()
        this.listMovies.addAll(listMovie)
    }
    inner class MovieViewHolder(private val binding:ItemsMovieBinding):RecyclerView.ViewHolder(binding.root) {
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
                cvMovie.setOnClickListener{
                    movieCallback.navigateToDetail(filmEntity)
                }
                Glide.with(itemView.context)
                    .load(filmEntity.imageMovie)
                    .apply{
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.error)
                    }
                    .into(ivMovie)
                imgShare.setOnClickListener{
                    movieCallback.movieShare(filmEntity)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        var binding=ItemsMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }
}