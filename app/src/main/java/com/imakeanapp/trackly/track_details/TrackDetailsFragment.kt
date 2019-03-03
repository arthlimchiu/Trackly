package com.imakeanapp.trackly.track_details


import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imakeanapp.domain.tracks.model.Track
import com.imakeanapp.trackly.tracks.view_model.TracksViewModel
import com.imakeanapp.trackly.R
import com.imakeanapp.trackly.core.SCREEN_TRACK_DETAILS

class TrackDetailsFragment : Fragment() {

    private lateinit var tracksViewModel: TracksViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var containerLayout: ConstraintLayout
    private lateinit var title: TextView
    private lateinit var trackImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var trackArtist: TextView
    private lateinit var trackPrice: TextView
    private lateinit var trackGenre: TextView
    private lateinit var trackDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        tracksViewModel = ViewModelProviders.of(requireNotNull(activity)).get(TracksViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_track_details, container, false)

        containerLayout = view.findViewById(R.id.container)
        toolbar = view.findViewById(R.id.fragment_toolbar)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolbar.visibility = View.GONE
        } else {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        title = view.findViewById(R.id.toolbar_title)
        trackImage = view.findViewById(R.id.track_image)
        trackName = view.findViewById(R.id.track_name)
        trackArtist = view.findViewById(R.id.track_artist)
        trackPrice = view.findViewById(R.id.track_price)
        trackGenre = view.findViewById(R.id.track_genre)
        trackDescription = view.findViewById(R.id.track_description)

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksViewModel.getCurrentTrack().observe(viewLifecycleOwner, Observer<Track> {
            containerLayout.visibility = View.VISIBLE

            Glide.with(this)
                .load(it.artworkUrl100)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(trackImage)

            val nameOfTrack = if (it.name.isEmpty()) {
                getString(R.string.no_track_name)
            } else {
                it.name
            }
            title.text = nameOfTrack
            trackName.text = nameOfTrack

            trackArtist.text = it.artist
            trackGenre.text = it.genre

            val priceText = if (it.price <= 0.0) {
                getString(R.string.free)
            } else {
                "${it.currency} ${it.price}"
            }
            trackPrice.text = priceText

            trackDescription.text = if (it.description.isEmpty()) {
                trackDescription.setTypeface(trackDescription.typeface, Typeface.ITALIC)
                getString(R.string.no_description_provided)
            } else {
                trackDescription.setTypeface(null, Typeface.NORMAL)
                it.description
            }
        })

        tracksViewModel.setCurrentScreen(SCREEN_TRACK_DETAILS)
    }
}
