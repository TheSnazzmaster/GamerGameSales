package com.honorsmobileapps.mathers.gamergamesales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.honorsmobileapps.mathers.gamergamesales.databinding.FragmentAddGameBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.MainFragmentBinding
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainFragmentDirections
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class AddGameFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val mAdapter = AddGameAdapter(viewModel.getGameList())
        binding.RecyclerView.adapter = mAdapter


        return rootView
    }

}