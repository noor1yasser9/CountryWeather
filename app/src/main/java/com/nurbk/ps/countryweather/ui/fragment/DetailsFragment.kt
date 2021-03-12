package com.nurbk.ps.countryweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.ParentDetailsAdapter
import com.nurbk.ps.countryweather.databinding.FragmentDealitsBinding
import com.nurbk.ps.countryweather.model.DetailsData
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.cities.City
import com.nurbk.ps.countryweather.model.countries.CountriesItem
import com.nurbk.ps.countryweather.model.countries.CountriesPageItem
import com.nurbk.ps.countryweather.model.photos.Photo
import com.nurbk.ps.countryweather.model.weather.currentweather.CurrentWeatherResponse
import com.nurbk.ps.countryweather.ui.dialog.LocationFragment
import com.nurbk.ps.countryweather.ui.viewmodel.CitiesViewModel
import com.nurbk.ps.countryweather.utils.ConstanceString.DATA_DETAILS
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var mBinding: FragmentDealitsBinding
    @Inject
     lateinit var viewModel: CitiesViewModel
    private lateinit var location: LocationFragment

    @Inject
    lateinit var parentAdapter: ParentDetailsAdapter

    @Inject
    lateinit var glide: RequestManager

    private lateinit var bundleDetails: Bundle

    private val detailsData: DetailsData by lazy {
        DetailsData(UUID.randomUUID().toString(), ArrayList())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentDealitsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        requireActivity().title = getString(R.string.detailsCountries)
        bundleDetails = requireArguments()
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        detailsData.dataList.clear()
        parentAdapter.data = detailsData

        lifecycleScope.launchWhenStarted {
            viewModel.getWeatherLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                        mBinding.progressBar.isVisible = true
                    }
                    Result.Status.SUCCESS -> {
                        mBinding.progressBar.isVisible = false
                        try {
                            val data = it.data as CurrentWeatherResponse
                            mBinding.chip5.apply {
                                text = data.main!!.temp.toString()
                                setOnClickListener {
                                    val bundleWeather = Bundle()
                                    bundleWeather.putInt("type", 2)
                                    findNavController().navigate(R.id.action_detailsFragment_to_weatherFragment,
                                        bundleWeather)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                    Result.Status.ERROR -> {
                        mBinding.progressBar.isVisible = false
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getCountryNameLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                        showLoading()
                    }
                    Result.Status.SUCCESS -> {
                        dismissLoading()
                        try {
                            detailsData.dataList.clear()
                            val data = it.data as CountriesItem
                            addDataFromBundle("Borders", data.borders, 0)
                            addDataFromBundle("Currencies", data.currencies, 1)
                            addDataFromBundle("Languages", data.languages, 2)
                            mBinding.item = data
                            location = LocationFragment()
                            mBinding.btnLocation.setOnClickListener {
                                if (!location.isAdded) {
                                    val bundles = Bundle()
                                    bundles.putParcelable(DATA_DETAILS, data)
                                    location.arguments = bundles
                                    location.show(requireActivity().supportFragmentManager, "")
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        mBinding
                    }
                    Result.Status.ERROR -> {
                        dismissLoading()
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.getCitiesLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        val data = it.data as ObjectDetails
                        addData(data, null, 3)
                    }
                    Result.Status.ERROR -> {
                    }
                }
            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel.getPhotosLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val data = it.data as ObjectDetails
                            addData(data, null, detailsData.dataList.size)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    Result.Status.ERROR -> {
                    }
                }
            }
        }
        mBinding.rcData.apply {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        mBinding.txtName.apply {
            setSingleLine()
            isSelected = true
        }
        mBinding.txtName.setSingleLine()
        mBinding.txtName.isSelected = true
        bundleDetails.getParcelable<CountriesPageItem>(DATA_DETAILS)?.let { item ->
            glide.load(item.countryInfo.flag).into(mBinding.imageView)
        }

        parentAdapter.onClick = object : ParentDetailsAdapter.OnClickListener {
            override fun onClickItemListener(data: Any) {
                if (data is City) {
                    viewModel.getWeatherCity(data.name)
                    val bundleWeather = Bundle()
                    bundleWeather.putInt("type", 1)
                    findNavController().navigate(
                        R.id.action_detailsFragment_to_weatherFragment, bundleWeather
                    )

                } else if (data is Photo) {
                    findNavController().navigate(R.id.action_detailsFragment_to_sliderFragment)
                }
            }

            override fun onClickSeeAllListener(i: Int) {
                findNavController().navigate(R.id.action_detailsFragment_to_seeAllFragment)
            }
        }
    }

    private fun addData(objectDetails: ObjectDetails, data: List<Any>?, index: Int?) {
        if (data != null)
            objectDetails.data.addAll(data)
        if (index != null)
            try {
                detailsData.dataList.add(index, objectDetails)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        else
            detailsData.dataList.add(objectDetails)
        parentAdapter.notifyItemChanged(detailsData.dataList.indexOf(objectDetails))
    }

    private fun addDataFromBundle(name: String, data: List<Any>, index: Int?) {
        val citiesObject =
            ObjectDetails(UUID.randomUUID().toString(), name, ArrayList(), 4)
        addData(citiesObject, data, index)
    }


    private fun showLoading() {
        mBinding.loading.isVisible = true
    }

    private fun dismissLoading() {
        mBinding.loading.isVisible = false
        mBinding.group.isVisible = true
    }

}