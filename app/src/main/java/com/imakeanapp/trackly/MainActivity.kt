package com.imakeanapp.trackly

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.imakeanapp.domain.session.model.Session
import com.imakeanapp.trackly.core.SCREEN_TRACKS
import com.imakeanapp.trackly.core.SCREEN_TRACK_DETAILS
import com.imakeanapp.trackly.core.injector
import com.imakeanapp.trackly.track_details.TrackDetailsFragment
import com.imakeanapp.trackly.tracks.view.TracksFragment
import com.imakeanapp.trackly.tracks.state.TracksState
import com.imakeanapp.trackly.tracks.view_model.TracksViewModel

class MainActivity : AppCompatActivity() {

    private val factory = injector.mainVMFactory()
    private lateinit var viewModel: TracksViewModel

    private var shouldFinish = false
    private var dualPane = false
    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dualPane = findViewById<FrameLayout>(R.id.fragment_details_container)?.visibility == View.VISIBLE

        viewModel = ViewModelProviders.of(this, factory).get(TracksViewModel::class.java)

        viewModel.state().observe(this, Observer<TracksState> {
            when (it) {
                is TracksState.SessionFetched -> {
                    this.session = it.session

                    showTracksFragment()
                }
                is TracksState.TracksFetched -> {
                    session?.let { session ->
                        if (session.lastScreen.contentEquals(SCREEN_TRACK_DETAILS)) {
                            viewModel.loadCurrentTrack()
                        }
                    }
                }
                is TracksState.TrackChanged -> {
                    if (!dualPane) {
                        showTrackDetailsFragment()
                    }
                }
                is TracksState.CurrentScreenSaved, is TracksState.ErrorSavingCurrentScreen -> {
                    if (shouldFinish) {
                        shouldFinish = false
                        finish()
                    }
                }
                is TracksState.ErrorFetchingSession -> showTracksFragment()
            }
        })

        // When orientation changes, Android saves the state
        // and restores the fragments back itself.
        // So we just check if the state is null, so that
        // we don't fetch the session again.
        if (savedInstanceState == null) {
            viewModel.getSession()
        } else if (dualPane) {
            showTrackDetailsFragment()
        }
    }

    private fun showTracksFragment() {
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(SCREEN_TRACKS)
            replace(R.id.fragment_container, TracksFragment())
            commit()
        }

        if (dualPane) {
            showTrackDetailsFragment()
        }
    }

    private fun showTrackDetailsFragment() {
        supportFragmentManager.beginTransaction().apply {
            if (dualPane) {
                supportFragmentManager.popBackStack(SCREEN_TRACKS, 0)
                replace(R.id.fragment_details_container, TrackDetailsFragment())
            } else {
                setCustomAnimations(R.animator.slide_in_from_right, 0, 0, R.animator.slide_out_to_right)
                addToBackStack(SCREEN_TRACK_DETAILS)
                add(R.id.fragment_container, TrackDetailsFragment())
            }

            commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            viewModel.setCurrentScreen(SCREEN_TRACKS)
            supportFragmentManager.popBackStack()
        } else {
            shouldFinish = true
            viewModel.setCurrentScreen(SCREEN_TRACKS)
        }
    }
}
