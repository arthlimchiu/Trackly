package com.imakeanapp.trackly.tracks.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imakeanapp.trackly.tracks.view_model.TracksViewModel
import com.imakeanapp.trackly.R
import com.imakeanapp.trackly.tracks.state.TracksState
import com.imakeanapp.trackly.core.DEFAULT_DATE_TIME_PATTERN
import com.imakeanapp.trackly.core.SingleEvent
import com.imakeanapp.trackly.tracks.state.TrackListListener
import com.imakeanapp.trackly.util.toTimeElapsed
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class TracksFragment : Fragment() {

    private lateinit var tracksViewModel: TracksViewModel

    private lateinit var toolbar: Toolbar
    private lateinit var lastVisited: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tracks: RecyclerView
    private lateinit var tracksAdapter: TracksAdapter

    private val trackListListener = MutableLiveData<SingleEvent<TrackListListener>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tracksViewModel = ViewModelProviders.of(requireNotNull(activity)).get(TracksViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tracks, container, false)

        toolbar = view.findViewById(R.id.fragment_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        lastVisited = view.findViewById(R.id.time_last_visited)
        progressBar = view.findViewById(R.id.progress_bar)

        tracks = view.findViewById(R.id.tracks)
        tracks.layoutManager = LinearLayoutManager(context)
        tracksAdapter = TracksAdapter(mutableListOf(), trackListListener)
        tracks.adapter = tracksAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tracksViewModel.state().observe(viewLifecycleOwner, Observer<TracksState> {
            when (it) {
                is TracksState.DateLastVisitedFetched -> {
                    lastVisited.text = if (it.lastVisited.isEmpty()) {
                        getString(R.string.last_visited, getString(R.string.just_now))
                    } else {
                        "${getString(R.string.last_visited, it.lastVisited.toTimeElapsed())} ${getString(R.string.ago)}"
                    }
                    saveLastVisited()
                }
                is TracksState.FetchingTracks -> {
                    showProgressBar()
                }
                is TracksState.TracksFetched -> {
                    tracksAdapter.updateTracks(it.tracks.toMutableList())
                    tracksViewModel.getLastVisited()
                    hideProgressBar()
                }
                is TracksState.ErrorFetchingTracks -> {
                    Toast.makeText(context, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
                is TracksState.ErrorFetchingDateLastVisited -> {
                    lastVisited.visibility = View.GONE
                    saveLastVisited()
                }
            }
        })

        trackListListener.observe(viewLifecycleOwner, Observer<SingleEvent<TrackListListener>> { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is TrackListListener.OnTrackClick -> {
                        tracksViewModel.setCurrentTrack(it.track)
                    }
                }
            }
        })

        tracksViewModel.getTracks()
    }

    private fun saveLastVisited() {
        tracksViewModel.saveLastVisited(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)))
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
