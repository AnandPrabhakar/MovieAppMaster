package com.movies.movieappmaster.views
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.movies.movieappmaster.R
import com.movies.movieappmaster.data.Movie

class MovieAdapter: PagingDataAdapter<Movie, ViewHolder>(REPO_COMPARATOR)
{




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return MovieViewHolder.create(parent)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

        val uiModel = getItem(position)
        uiModel.let {
            ( holder as MovieViewHolder).bind(it)

        }
    }



    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}

