package com.ataulm.artcollector.gallery

import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.gallery.data.AndroidGalleryRepository
import com.ataulm.artcollector.gallery.domain.GalleryRepository
import com.ataulm.artcollector.gallery.ui.GalleryActivity
import com.ataulm.artcollector.gallery.ui.PaintingsViewModel
import com.ataulm.artcollector.gallery.ui.GalleryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal object GalleryModule {

    @JvmStatic
    @Provides
    fun paintingsRepository(galleryRepository: AndroidGalleryRepository): GalleryRepository {
        return galleryRepository
    }

    @JvmStatic
    @Provides
    fun viewModel(activity: GalleryActivity, viewModelFactory: GalleryViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(PaintingsViewModel::class.java)
}
