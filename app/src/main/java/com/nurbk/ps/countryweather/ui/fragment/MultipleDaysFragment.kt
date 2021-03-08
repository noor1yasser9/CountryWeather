package com.nurbk.ps.countryweather.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.adapters.WeatherAdapter
import com.nurbk.ps.countryweather.databinding.FragmentMultipleDaysBinding
import com.nurbk.ps.countryweather.model.weather.daysweather.MultipleDaysWeatherResponse
import com.nurbk.ps.countryweather.ui.viewmodel.WeatherViewModel
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MultipleDaysFragment : BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentMultipleDaysBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter()
    }

    var onChange: OnStateChangedDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = FragmentMultipleDaysBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }

        weatherAdapter.type = 0
        return mBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnClose.setOnClickListener {
            dismiss()
        }
        mBinding.recyclerView.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getDaysWeatherLiveData().collect {
                when (it.status) {
                    Result.Status.LOADING -> {

                    }
                    Result.Status.SUCCESS -> {
                        try {
                            val item = it.data as MultipleDaysWeatherResponse
                            weatherAdapter.dataDay = item.list!!
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    Result.Status.ERROR -> {

                    }
                }
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val d = super.onCreateDialog(savedInstanceState)
        d.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById(R.id.design_bottom_sheet) as FrameLayout?
            val behaviour: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        // Bottom Sheet was dismissed by user! But this is only fired, if dialog is swiped down! Not if touch outside dismissed the dialog or the back button
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val slide = bottomSheet.width * slideOffset
                    onChange?.let {
                        it.onChanged(slide)
                    }
                }
            })
        }
        return d
    }

    interface OnStateChangedDialog {
        fun onChanged(setOff: Float)
    }
}