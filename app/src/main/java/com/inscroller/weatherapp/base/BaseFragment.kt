package com.inscroller.weatherapp.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.inscroller.weatherapp.utils.AppSharedPref
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel>() : Fragment() {
    open lateinit var viewModel: T
    abstract fun setViewModel(): T

    @Inject
    open lateinit var sharedPref: AppSharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = setViewModel()
    }

    fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    fun back() {
        findNavController().popBackStack()
    }

    fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}