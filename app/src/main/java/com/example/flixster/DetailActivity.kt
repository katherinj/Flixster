package com.example.flixster

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var releaseDateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var originalLanguageTextView: TextView
    private lateinit var voteCountTextView: TextView
    private lateinit var voteAverageTextView: TextView
    private lateinit var popularityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.media_detail)

        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        releaseDateTextView = findViewById(R.id.release_date)
        descriptionTextView = findViewById(R.id.description)
        originalLanguageTextView = findViewById(R.id.originalLanguage)
        voteCountTextView = findViewById(R.id.voteCount)
        voteAverageTextView = findViewById(R.id.voteAverage)
        popularityTextView = findViewById(R.id.popularity)
        val mediaItem = intent.getSerializableExtra(MEDIA_EXTRA)

        if (mediaItem is Movie) {
            titleTextView.text = mediaItem.title
            releaseDateTextView.text = mediaItem.releaseDate
            descriptionTextView.text = mediaItem.description ?: "No description available"
            originalLanguageTextView.text = mediaItem.originalLanguage
            voteCountTextView.text = mediaItem.voteCount.toString()
            voteAverageTextView.text = mediaItem.voteAverage.toString()
            popularityTextView.text = mediaItem.popularity.toString()
            val title = mediaItem.title

            val transitionName = intent.getStringExtra("transition_name")
            mediaImageView.transitionName = transitionName

            val imageUrl = "https://image.tmdb.org/t/p/w500/${intent.getStringExtra("image_url")}"
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(RoundedCorners(100))
                .into(mediaImageView)

        } else if (mediaItem is TvShow) {
            titleTextView.text = mediaItem.title
            descriptionTextView.text = mediaItem.description ?: "No description available"
            releaseDateTextView.text = mediaItem.releaseDate
            originalLanguageTextView.text = mediaItem.originalLanguage
            voteCountTextView.text = mediaItem.voteCount.toString()
            voteAverageTextView.text = mediaItem.voteAverage.toString()
            popularityTextView.text = mediaItem.popularity.toString()

            val imageUrl = "https://image.tmdb.org/t/p/w500/${mediaItem.posterPath}"

            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(RoundedCorners(100))
                .into(mediaImageView)
        }
    }
}
