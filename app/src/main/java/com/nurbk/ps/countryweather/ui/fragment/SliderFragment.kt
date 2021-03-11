package com.nurbk.ps.countryweather.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nurbk.ps.countryweather.adapters.ItemPagerSlider
import com.nurbk.ps.countryweather.databinding.FragmentSliderBinding
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.photos.Photo
import com.nurbk.ps.countryweather.ui.viewmodel.CitiesViewModel
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class SliderFragment : Fragment() {

    private lateinit var mBinding: FragmentSliderBinding
    private val viewModel: CitiesViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    private val adapterSlider by lazy {
        ItemPagerSlider(glide, listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentSliderBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getPhotosLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val data = it.data as ObjectDetails
                            adapterSlider.listData = data.data as List<Photo>
                            adapterSlider.notifyDataSetChanged()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                    Result.Status.ERROR -> {
                    }
                }
            }
        }


        mBinding.slider.apply {
            adapter = adapterSlider
        }

        adapterSlider.setItemClickListener {
            shareImage(photo = it)
        }
    }


    private fun shareImage(photo: Photo) {
        glide.asBitmap()
            .load(getUrl(photo))
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?,
                ) {
                    val share = Intent(Intent.ACTION_SEND)
                    share.type = "image/jpeg"
                    val bytes = ByteArrayOutputStream()
                    resource.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path: String =
                        MediaStore.Images.Media.insertImage(requireActivity().contentResolver,
                            resource,
                            "Title",
                            null)
                    val imageUri = Uri.parse(path)
                    share.putExtra(Intent.EXTRA_STREAM, imageUri)
                    startActivity(Intent.createChooser(share, "Select"))
                }
            })




    }

    private fun getUrl(photo: Photo): String {
        return "https://live.staticflickr.com/" + photo.server
            .toString() + "/" + photo.id.toString() + "_" + photo.secret
            .toString() + "_w.jpg"
    }
}