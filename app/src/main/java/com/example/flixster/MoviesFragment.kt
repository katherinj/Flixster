package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private const val API_KEY = "5d1403663d86ab6d840d02c7e2a19ee0"
private const val BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/popular"
private const val BASE_TV_URL = "https://api.themoviedb.org/3/tv/popular"

class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    private lateinit var progressMovies: ContentLoadingProgressBar
    private lateinit var progressTvShows: ContentLoadingProgressBar
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var tvShowsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        progressMovies = view.findViewById(R.id.progressMovies)
        progressTvShows = view.findViewById(R.id.progressTvShows)
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView)
        tvShowsRecyclerView = view.findViewById(R.id.tvShowsRecyclerView)

        moviesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tvShowsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        fetchMovies()
        fetchTvShows()

        return view
    }

    private fun fetchMovies() {
        progressMovies.show()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client.get(BASE_MOVIE_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                progressMovies.hide()
                val resultsJSON: JSONArray = json.jsonObject.getJSONArray("results")
                val gson = Gson()
                val movieListType = object : TypeToken<List<Movie>>() {}.type
                val movies: List<Movie> = gson.fromJson(resultsJSON.toString(), movieListType)

                moviesRecyclerView.adapter = MoviesRecyclerViewAdapter(movies, this@MoviesFragment)
                Log.d("MoviesFragment", "Loaded Popular Movies: $movies")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressMovies.hide()
                Log.e("MoviesFragment", "Error fetching popular movies: $errorResponse")
            }
        })
    }

    private fun fetchTvShows() {
        progressTvShows.show()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client.get(BASE_TV_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                progressTvShows.hide()
                val resultsJSON: JSONArray = json.jsonObject.getJSONArray("results")
                val gson = Gson()
                val tvShowListType = object : TypeToken<List<TvShow>>() {}.type  // Correct model
                val tvShows: List<TvShow> = gson.fromJson(resultsJSON.toString(), tvShowListType)

                tvShowsRecyclerView.adapter = TvShowsRecyclerViewAdapter(tvShows, this@MoviesFragment) // Correct Adapter
                Log.d("MoviesFragment", "Loaded Popular TV Shows: $tvShows")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressTvShows.hide()
                Log.e("MoviesFragment", "Error fetching popular TV shows: $errorResponse")
            }
        })
    }

    override fun onItemClick(item: Any) {
        when (item) {
            is Movie -> {
                Toast.makeText(context, "Movie: ${item.title}", Toast.LENGTH_SHORT).show()
            }
            is TvShow -> {
                Toast.makeText(context, "TV Show: ${item.title}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Log.e("MoviesFragment", "Unknown item clicked")
            }
        }
    }
}
