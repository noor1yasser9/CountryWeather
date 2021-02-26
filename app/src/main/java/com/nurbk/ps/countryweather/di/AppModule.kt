package com.nurbk.ps.countryweather.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object  AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
//            .placeholder(R.drawable.ic_image)
//            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

//    @Singleton
//    @Provides
//    fun genericRequestBuilder(
//        @ApplicationContext context: Context
//    ) = Glide.with(this)
//        .using(Glide.buildStreamModelLoader(Uri::class.java, this), InputStream::class.java)
//        .from(Uri::class.java)
//        .`as`(SVG::class.java)
//        .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
//        .sourceEncoder(StreamEncoder())
//        .cacheDecoder(FileToStreamDecoder<SVG>(SvgDecoder()))
//        .decoder(SvgDecoder())
//        .placeholder(R.drawable.image_loading)
//        .error(R.drawable.image_error)
//        .animate(R.anim.fade_in)
//        .listener(SvgSoftwareLayerSetter<Uri>())


}