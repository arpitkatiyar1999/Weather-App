package com.inscroller.weatherapp.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.inscroller.weatherapp.R
import com.inscroller.weatherapp.base.BaseFragment
import com.inscroller.weatherapp.databinding.FragmentSplashBinding
import com.inscroller.weatherapp.utils.Constants.permissionList
import com.inscroller.weatherapp.utils.Network
import com.inscroller.weatherapp.utils.State
import com.inscroller.weatherapp.utils.makeGone
import com.inscroller.weatherapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var networkUtil: Network

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeListeners()
    }

    private fun initUi() {
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.getCurrentCity()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(permissionList)
    }

    private fun observeListeners() {
        observeCurrentLocationLiveData()
        observeCurrentWeatherLiveData()
    }

    private fun observeCurrentWeatherLiveData() {
        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                with(binding) {
                    when (it) {
                        is State.Loading -> {
                            progressPb.makeVisible()
                        }

                        is State.Success -> {
                            progressPb.makeGone()
                            val action = SplashFragmentDirections.actionSplashFragmentToWeatherDetailsFragment(it.data)
                            navigate(action)
                        }

                        is State.Error -> {
                            Log.e("abc", "observeCurrentWeatherLiveData: ${it.error}")
                            progressPb.makeGone()
                            createErrorDialog(
                                it.errorMessage ?: getString(R.string.some_unknown_error_occurred)
                            )
                        }
                    }
                }
            }

        }
    }

    private fun observeCurrentLocationLiveData() {
        viewModel.getLocationLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                with(binding) {
                    when (it) {
                        is State.Loading -> {
                            progressPb.makeVisible()
                        }

                        is State.Success -> {
                            progressPb.makeGone()
                            getCurrentWeatherData(it.data)

                        }

                        is State.Error -> {
                            progressPb.makeGone()
                            createErrorDialog(
                                it.errorMessage ?: getString(R.string.some_unknown_error_occurred)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentWeatherData(coordinates: String) {
        Log.e("abc", "getCurrentWeatherData: $coordinates")
        if (networkUtil.hasNetwork()) {
            viewModel.getCurrentWeather(coordinates)
        } else {
            createErrorDialog(getString(R.string.no_internet_connection), isNoInternet = true)
        }
    }

    private fun createErrorDialog(error: String, isNoInternet: Boolean = false) {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
        if (isNoInternet) {
            dialogBuilder.setIcon(R.drawable.ic_no_internet)
        } else {
            dialogBuilder.setIcon(R.drawable.ic_error)
        }
        dialogBuilder.setTitle(getString(R.string.some_error_occurred))
        dialogBuilder.setMessage(error)
        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
            requireActivity().finish()
        }
        dialogBuilder.setCancelable(false)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setViewModel(): SplashViewModel {
        return ViewModelProvider(this)[SplashViewModel::class.java]
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val grantedPermissions = mutableListOf<String>()
            for (entry in permissions.entries) {
                val permission = entry.key
                val isGranted = entry.value
                if (isGranted) {
                    grantedPermissions.add(permission)
                }
            }
            if (grantedPermissions.size == permissionList.size) {
                viewModel.getCurrentCity()
            } else {
                toast(getString(R.string.location_permission_not_granted))
                requireActivity().finish()
            }
        }

}
