package com.projek.submissionjetpack1.ui.detailFilm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projek.submissionjetpack1.databinding.GenreFilmBinding

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val listGenre = ArrayList<String>()

    fun setListGenre(genres: List<String>?) {
        if (genres == null) return
        this.listGenre.clear()
        this.listGenre.addAll(genres)
    }

    inner class GenreViewHolder(private val binding: GenreFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) {
            with(binding) {
                textGenre.text = genre
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }

}
