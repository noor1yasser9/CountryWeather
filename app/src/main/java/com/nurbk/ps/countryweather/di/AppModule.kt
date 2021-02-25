package com.nurbk.ps.countryweather.di

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import com.nurbk.ps.countryweather.utils.SvgDecoder
import com.nurbk.ps.countryweather.utils.SvgDrawableTranscoder
import com.nurbk.ps.countryweather.utils.SvgSoftwareLayerSetter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object  AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context)


    @Singleton
    @Provides
    fun genericRequestBuilder(
        @ApplicationContext context: Context
    ) = Glide.with(context)
        .using(
            Glide.buildStreamModelLoader(Uri::class.java, context),
            InputStream::class.java
        )
        .from(Uri::class.java)
        .`as`(SVG::class.java)
        .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
        .sourceEncoder(StreamEncoder())
        .cacheDecoder(FileToStreamDecoder<SVG>(SvgDecoder()))
        .decoder(SvgDecoder())
        .listener(SvgSoftwareLayerSetter<Uri>())


}