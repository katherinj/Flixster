package com.example.flixster

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MoviesRecyclerViewAdapter
    (private val movies: List<Movie>, private val mListener: OnListFragmentInteractionListener?)
    :RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(R.id.movie_description) as
                TextView
        val mMoviePoster: ImageView = mView.findViewById<View>(R.id.movie_poster) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieDescription.text + "'"
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        val isPortrait = holder.mView.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val moviePosterPathURL = "https://image.tmdb.org/t/p/w500/" + movie.posterPath
        val movieBackdropPathURL = "https://image.tmdb.org/t/p/w1280/" + movie.backdropPath

        val imageUrl = if (isPortrait) moviePosterPathURL else movieBackdropPathURL

        val layoutParams = holder.mMoviePoster.layoutParams
        if (isPortrait) {
            layoutParams.width = holder.mView.resources.displayMetrics.widthPixels / 3
            layoutParams.height = (layoutParams.width * 1.5).toInt()
            holder.mMovieDescription.maxLines = 8
            holder.mMovieTitle.textSize = 18f
            holder.mMovieDescription.textSize = 14f
        } else {
            layoutParams.width = holder.mView.resources.displayMetrics.widthPixels / 2
            layoutParams.height = (layoutParams.width * 0.6).toInt()
            holder.mMovieDescription.maxLines = 12
            holder.mMovieTitle.textSize = 24f
            holder.mMovieDescription.textSize = 22f
        }
        holder.mMoviePoster.layoutParams = layoutParams

        Glide.with(holder.mView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.mMoviePoster)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }


    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}