package com.nurbk.ps.countryweather.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nurbk.ps.countryweather.databinding.FragmentLocationBinding
import com.nurbk.ps.countryweather.model.countries.CountriesItem
import com.nurbk.ps.countryweather.utils.ConstanceString

class LocationFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private lateinit var mBinding: FragmentLocationBinding
    private lateinit var bundle: Bundle
    private lateinit var countriesItem: CountriesItem
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentLocationBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireArguments().getParcelable<CountriesItem>(ConstanceString.DATA_DETAILS)?.let {
            countriesItem=it
            mBinding.mapView.getMapAsync(this)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    override fun onMapReady(map: GoogleMap) {
        val latLng = LatLng(
            countriesItem.latlng[0],
            countriesItem.latlng[1]
        )
        map.addMarker(
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
        mBinding.mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        mBinding.mapView.onResume()
        super.onResume()
    }

    override fun onStop() {
        mBinding.mapView.onStop()
        super.onStop()
    }

    override fun onPause() {
        mBinding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mBinding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mBinding.mapView.onLowMemory()
        super.onLowMemory()
    }


}