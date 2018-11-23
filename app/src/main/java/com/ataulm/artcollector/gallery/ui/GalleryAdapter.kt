package com.ataulm.artcollector.gallery.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ataulm.artcollector.R
import com.ataulm.artcollector.gallery.domain.Artist
import com.ataulm.artcollector.gallery.domain.Painting
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class GalleryAdapter constructor(
        private val glideRequestManager: RequestManager,
        private val onClick: (Painting, View) -> Unit,
        private val onClickArtist: (Artist) -> Unit
) : ListAdapter<Painting, GalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(glideRequestManager, onClick, onClickArtist, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<Painting>() {
        override fun areItemsTheSame(oldItem: Painting, newItem: Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Painting, newItem: Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val glideRequestManager: RequestManager,
            private val onClick: (Painting, View) -> Unit,
            private val onClickArtist: (Artist) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Painting) {
            itemView.setOnClickListener { onClick(item, itemView.imageView) }
            itemView.artistTextView.text = item.artist.name
            itemView.artistTextView.setOnClickListener { onClickArtist(item.artist) }
            glideRequestManager
                    .load(item.imageUrl)
                    .into(itemView.imageView)
        }
    }
}
