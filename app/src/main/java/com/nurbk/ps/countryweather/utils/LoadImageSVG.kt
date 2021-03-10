package com.nurbk.ps.countryweather.utils


import android.content.Context
import android.widget.ImageView
import com.pixplicity.sharp.Sharp
import okhttp3.*
import java.io.IOException
import java.io.InputStream


object LoadImageSVG {
    private var httpClient: OkHttpClient? = null



    // this method is used to fetch svg and load it into target imageview.
    fun fetchSvg(context: Context, url: String, target: ImageView) {
        if (httpClient == null) {
            httpClient = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 15 * 1024 * 1014))
                .build()
        }

        // here we are making HTTP call to fetch data from URL.
            val request: Request = Request.Builder().url(url).build()
            httpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // we are adding a default image if we gets any error.
//                target.setImageResource(R.drawable.gfgimage)
                }


                override fun onResponse(call: Call, response: Response) {
                    // sharp is a library which will load stream which we generated
                    // from url in our target imageview.
                    val stream: InputStream = response.body!!.byteStream()
                    try {
                        Sharp.loadInputStream(stream).into(target)
                        stream.close()
                    } catch (e: java.lang.Exception) {

                    }

                }
            })


    }
}