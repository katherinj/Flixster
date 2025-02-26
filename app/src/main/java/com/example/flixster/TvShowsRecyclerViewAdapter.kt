package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TvShowsRecyclerViewAdapter(
    private val tvShows: List<TvShow>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TvShowsRecyclerViewAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false) // ✅ Correct layout
        return TvShowViewHolder(view)
    }

    inner class TvShowViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: TvShow? = null
        val mShowPoster: ImageView = mView.findViewById(R.id.movie_poster)
        val mShowTitle: TextView = mView.findViewById(R.id.movie_title)

        override fun toString(): String {
            return mShowTitle.text.toString()
        }
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val show = tvShows[position]

        holder.mItem = show
        holder.mShowTitle.text = show.title

        val imageUrl = "https://image.tmdb.org/t/p/w500/" + show.posterPath

        Glide.with(holder.mView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.mShowPoster)

        holder.mView.setOnClickListener {
            holder.mItem?.let { show ->
                mListener?.onItemClick(show)
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
}
