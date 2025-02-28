package com.example.flixster

import android.view.View

/**
 * This interface is used by the [MoviesRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [MediaFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: Any, sharedView: View)
}
