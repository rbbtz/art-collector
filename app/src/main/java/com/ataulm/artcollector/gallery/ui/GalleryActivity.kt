package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.*
import com.ataulm.artcollector.gallery.injectDependencies
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.itemview_painting.view.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: GalleryViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val adapter = GalleryAdapter(glideRequestManager)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GallerySpacingItemDecoration(resources))

        viewModel.gallery.observe(this, DataObserver<UiGallery> { gallery ->
            adapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver { command ->
            when (command) {
                is NavigateToArtistGallery -> navigateToArtistGallery(command)
                is NavigateToPainting -> navigateToPainting(command)
            }
        })
    }

    private fun navigateToArtistGallery(it: NavigateToArtistGallery) {
        val intent = artistGalleryIntent(it.artistId)
        startActivity(intent)
    }

    private fun navigateToPainting(command: NavigateToPainting) {
        val (painting, adapterPosition) = command.painting to command.adapterPosition
        val paintingIntent = paintingIntent(painting.artistId, painting.id, painting.imageUrl)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, recyclerView.sharedElements(adapterPosition))
        startActivity(paintingIntent, options.toBundle())
    }

    private fun RecyclerView.sharedElements(adapterPosition: Int): Pair<View, String> {
        val itemView = layoutManager?.findViewByPosition(adapterPosition)!!
        return Pair(itemView.imageView as View, getString(R.string.shared_element_painting))
    }
}
