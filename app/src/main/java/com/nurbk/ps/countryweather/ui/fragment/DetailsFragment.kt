package com.nurbk.ps.countryweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.ParentDetailsAdapter
import com.nurbk.ps.countryweather.databinding.FragmentDealitsBinding
import com.nurbk.ps.countryweather.model.DetailsData
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.countries.CountriesItem
import com.nurbk.ps.countryweather.model.weather.currentweather.CurrentWeatherResponse
import com.nurbk.ps.countryweather.ui.viewmodel.CitiesViewModel
import com.nurbk.ps.countryweather.utils.ConstanceString.DATA_DETAILS
import com.nurbk.ps.countryweather.utils.LoadImageSVG
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mBinding: FragmentDealitsBinding
    private val viewModel: CitiesViewModel by viewModels()

    @Inject
    lateinit var parentAdapter: ParentDetailsAdapter


    private lateinit var countriesItem: CountriesItem
    private lateinit var bundle: Bundle

    private val detailsData: DetailsData by lazy {
        DetailsData(UUID.randomUUID().toString(), ArrayList())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDealitsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        requireActivity().title = getString(R.string.detailsCountries)

        bundle = requireArguments()
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsData.dataList.clear()
        parentAdapter.data = detailsData
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
            viewModel.getWeatherLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {
                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val data = it.data as CurrentWeatherResponse
                            mBinding.chip5.apply {
                                text = data.main!!.temp.toString()
                                setOnClickListener {
//                                countriesViewModel.getWeather(countriesItem.name)
                                    findNavController().navigate(R.id.action_detailsFragment_to_weatherFragment)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
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
                            addData(data, null, null)
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

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.txtName.apply {
            setSingleLine()
            isSelected = true
        }
        mBinding.txtName.setSingleLine()
        mBinding.txtName.isSelected = true
        bundle.getParcelable<CountriesItem>(DATA_DETAILS)?.let { item ->
            countriesItem = item
            mBinding.item = countriesItem

            LoadImageSVG.fetchSvg(requireContext(), countriesItem.flag, mBinding.imgFlag);

            addDataFromBundle("Borders", item.borders, 0)
            addDataFromBundle("Currencies", item.currencies, 1)
            addDataFromBundle("Languages", item.languages, 2)

            mBinding.mapView.getMapAsync(this)
        }
    }

    private fun addData(objectDetails: ObjectDetails, data: List<Any>?, index: Int?) {
        if (data != null)
            objectDetails.data.addAll(data)
        if (index != null)
            detailsData.dataList.add(index, objectDetails)
        else
            detailsData.dataList.add(objectDetails)
        parentAdapter.notifyItemChanged(detailsData.dataList.indexOf(objectDetails))
    }

    private fun addDataFromBundle(name: String, data: List<Any>, index: Int) {
        val citiesObject =
            ObjectDetails(UUID.randomUUID().toString(), name, ArrayList(), 4)
        addData(citiesObject, data, index)
    }


    override fun onMapReady(map: GoogleMap?) {
        val latLng = LatLng(
            countriesItem.latlng[0],
            countriesItem.latlng[1]
        )
        map!!.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(countriesItem.name)
        )
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng, 10f
            )
        )
        map.uiSettings.setAllGesturesEnabled(true)
        map.uiSettings.isZoomGesturesEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.isTrafficEnabled = true

    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

//
//    override fun onPause() {
//        super.onPause()
//        mBinding.mapView.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mBinding.mapView.onStop()
//    }


    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

}