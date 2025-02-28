package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
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

class MediaFragment : Fragment(), OnListFragmentInteractionListener {

    private lateinit var progressMovies: ContentLoadingProgressBar
    private lateinit var moviesRecyclerView: RecyclerView

    private lateinit var progressTvShows: ContentLoadingProgressBar
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

        moviesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
            false)
        tvShowsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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

                moviesRecyclerView.adapter = MoviesRecyclerViewAdapter(movies, this@MediaFragment)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressMovies.hide()
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
                val tvShowListType = object : TypeToken<List<TvShow>>() {}.type
                val tvShows: List<TvShow> = gson.fromJson(resultsJSON.toString(), tvShowListType)

                tvShowsRecyclerView.adapter = TvShowsRecyclerViewAdapter(tvShows, this@MediaFragment)
                Log.d("MediaFragment", "Loaded Popular TV Shows: $tvShows")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressTvShows.hide()
                Log.e("MediaFragment", "Error fetching popular TV shows: $errorResponse")
            }
        })
    }

    override fun onItemClick(item: Any, sharedView: View) {
        val context = requireContext()

        val intent = Intent(context, DetailActivity::class.java)

        when (item) {
            is Movie -> {
                intent.putExtra("title", item.title)
                intent.putExtra("image_url", item.posterPath)
                intent.putExtra("transition_name", sharedView.transitionName)
                intent.putExtra(MEDIA_EXTRA, item)


                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    sharedView, sharedView.transitionName
                )

                startActivity(intent, options.toBundle())
            }

            is TvShow -> {
                intent.putExtra("title", item.title)
                intent.putExtra("image_url", item.posterPath)
                intent.putExtra("transition_name", sharedView.transitionName)
                intent.putExtra(MEDIA_EXTRA, item)


                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    sharedView, sharedView.transitionName
                )

                startActivity(intent, options.toBundle())
            }
        }
    }

}
