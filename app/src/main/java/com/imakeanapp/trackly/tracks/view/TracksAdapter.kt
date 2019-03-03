package com.imakeanapp.trackly.tracks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imakeanapp.domain.tracks.model.Track
import com.imakeanapp.trackly.R
import com.imakeanapp.trackly.core.SingleEvent
import com.imakeanapp.trackly.tracks.state.TrackListListener

class TracksAdapter(
    private var tracks: MutableList<Track>,
    private val listener: MutableLiveData<SingleEvent<TrackListListener>>
) : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

    fun updateTracks(newTracks: MutableList<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trackName: TextView = itemView.findViewById(R.id.track_name)
        private val trackPrice: TextView = itemView.findViewById(R.id.track_price)
        private val trackGenre: TextView = itemView.findViewById(R.id.track_genre)
        private val trackImage: ImageView = itemView.findViewById(R.id.track_image)

        fun bind(track: Track) {
            itemView.setOnClickListener {
                listener.value = SingleEvent(TrackListListener.OnTrackClick(track))
            }

            trackName.text = if (track.name.isEmpty()) {
                itemView.context.getString(R.string.no_track_name)
            } else {
                track.name
            }

            trackGenre.text = track.genre

            val priceText = if (track.price <= 0.0) {
                itemView.context.getString(R.string.free)
            } else {
                "${track.currency} ${track.price}"
            }
            trackPrice.text = priceText

            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(trackImage)
        }
    }
}