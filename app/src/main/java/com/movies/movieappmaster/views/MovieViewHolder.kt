package com.movies.movieappmaster.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.movies.movieappmaster.R
import com.movies.movieappmaster.data.Movie

class MovieViewHolder (view: View) : RecyclerView.ViewHolder(view)
{
    private var movie: Movie? = null
    private val poster: ImageView = view.findViewById(R.id.poster)
    private val name1: TextView = view.findViewById(R.id.name)
    private val year: TextView = view.findViewById(R.id.year)

    fun bind(movie: Movie?)
    {
        if (movie == null) {
            val resources = itemView.resources

        } else {
            showRepoData(movie)
        }
    }

    private fun showRepoData(repo: Movie) {
        this.movie = repo
        name1.text = repo.name
        year.text = repo.year
        Glide.with(itemView)  //2
            .load(repo.image_url).into(poster)//3



    }
    companion object
    {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item_layout, parent, false)
            return MovieViewHolder(view)
        }
    }

}