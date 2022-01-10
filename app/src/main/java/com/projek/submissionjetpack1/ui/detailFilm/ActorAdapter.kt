package com.projek.submissionjetpack1.ui.detailFilm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.databinding.ItemActorBinding

class ActorAdapter(private val callback: ActorCallback) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {
    private val castings = ArrayList<Casting>()

    fun setActor(arrayListActor: List<Casting>?) {
        if (arrayListActor == null){return}
        else if(this.castings.isEmpty()){
            this.castings.clear()
            this.castings.addAll(arrayListActor)
        }
    }

    inner class ActorViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(castingLeft: Casting, castingRight: Casting?) {
            with(binding) {
                tvActorLeft.text = castingLeft.nameCaster
                if(castingLeft.imageCaster!="") {
                    Glide.with(itemView.context)
                        .load(castingLeft.imageCaster)
                        .apply {
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.error)
                        }
                        .into(ivActorLeft)
                }
                typeLeft.text = "(${castingLeft.typeCast})"
                linearLayout.setOnClickListener {
                    callback.castingClicked(castingLeft)
                }

                if (castingRight != null) {
                    tvActorRight.text = castingRight.nameCaster
                    typeRight.text = "(${castingRight.typeCast})"
                    if(castingRight.imageCaster!="") {
                        Glide.with(itemView.context)
                            .load(castingRight.imageCaster)
                            .apply {
                                RequestOptions.placeholderOf(R.drawable.ic_loading)
                                    .error(R.drawable.error)
                            }
                            .into(ivActorRight)
                    }
                    linearLayout2.setOnClickListener {
                        callback.castingClicked(castingRight)
                    }
                } else {
                    linearLayout2.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorAdapter.ActorViewHolder {
        var binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        if (castings.size % 2 == 0) {
            holder.bind(castings[position * 2], castings[(position * 2) + 1])
        } else {
            if (position == itemCount - 1) {
                holder.bind(castings[position * 2], null)
            } else {
                holder.bind(castings[position * 2], castings[(position * 2) + 1])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (castings.size % 2 == 0) {
            castings.size / 2
        } else {
            (castings.size / 2) + 1
        }
    }

}
