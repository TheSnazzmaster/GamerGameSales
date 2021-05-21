package com.honorsmobileapps.mathers.gamergamesales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.honorsmobileapps.mathers.gamergamesales.databinding.MainFragmentBinding
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class AddGameFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

}