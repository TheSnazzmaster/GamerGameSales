package com.honorsmobileapps.mathers.gamergamesales.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.honorsmobileapps.mathers.gamergamesales.R

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

}
