package com.honorsmobileapps.mathers.gamergamesales

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequest
import com.honorsmobileapps.mathers.gamergamesales.databinding.FragmentAboutBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.MainFragmentBinding
import com.honorsmobileapps.mathers.gamergamesales.ui.main.BackgroundPriceUpdater
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainFragmentDirections
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class AboutFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding : FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.toHomeFromAboutImageButton.setOnClickListener {
            rootView.findNavController().popBackStack()
        }

        return rootView
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}