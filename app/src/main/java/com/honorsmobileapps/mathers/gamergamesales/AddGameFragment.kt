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

    val gameSaleInfoList: MutableList<GameSaleInfo> = mutableListOf(
        GameSaleInfo("Fortnite 2: Fort Harder", 9.99),
        GameSaleInfo("Among Us Battle Royale", 29.99),
        GameSaleInfo("Haywire",420.69),
        GameSaleInfo("Red Dead Rodyushkin", 79.99)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val mAdapter = GameSaleInfoAdapter(gameSaleInfoList)
        binding.RecyclerView.adapter = mAdapter


        return rootView
    }

}