package com.example.flixster

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

const val MEDIA_EXTRA = "MEDIA_EXTRA"

class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false) // Use updated layout
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMoviePoster: ImageView = mView.findViewById(R.id.poster)
        val mMovieTitle: TextView = mView.findViewById(R.id.title)
        val mMovieDescription: TextView = mView.findViewById(R.id.description)
        val mMovieReleaseDate: TextView = mView.findViewById(R.id.release_date)

        override fun toString(): String {
            return mMovieTitle.text.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description
        holder.mMovieReleaseDate.text = movie.releaseDate

        val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
        val requestOptions = RequestOptions().transform(RoundedCorners(30))

        Glide.with(holder.mView)
            .load(imageUrl)
            .apply(requestOptions)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.mMoviePoster)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie, holder.mMoviePoster)
            }
        }
    }


    override fun getItemCount(): Int {
        return movies.size
    }
}
