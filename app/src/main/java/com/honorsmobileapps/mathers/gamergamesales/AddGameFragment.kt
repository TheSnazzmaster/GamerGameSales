package com.honorsmobileapps.mathers.gamergamesales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.honorsmobileapps.mathers.gamergamesales.databinding.FragmentAddGameBinding
import com.honorsmobileapps.mathers.gamergamesales.ui.main.MainViewModel

class AddGameFragment : Fragment() {

//    private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!
    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        val rootView = binding.root


        var mAdapter = AddGameAdapter(viewModel.getOnlineGameList()!!) { gameSaleInfo: GameSaleInfo ->
            viewModel.addGame(gameSaleInfo)
            Toast.makeText(getActivity(), "help" + gameSaleInfo.name, Toast.LENGTH_SHORT).show()
        }
        binding.RecyclerView.adapter = mAdapter

        binding.searchButton.setOnClickListener {
            Toast.makeText(getActivity(),"please work" + binding.searchEditText.text.toString(),Toast.LENGTH_SHORT).show()
            viewModel.search(binding.searchEditText.text.toString())
            mAdapter.notifyDataSetChanged()

        }

        viewModel.onlineGameList.observe(viewLifecycleOwner){newList->
            mAdapter.notifyDataSetChanged()
            var thing = viewModel.getOnlineGameList()!![viewModel.getOnlineGameList()!!.size-1].name
            Toast.makeText(activity,thing,Toast.LENGTH_SHORT).show()
            mAdapter = AddGameAdapter(viewModel.getOnlineGameList()!!) { gameSaleInfo: GameSaleInfo ->
                viewModel.addGame(gameSaleInfo)
                mAdapter.notifyDataSetChanged()
            }
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()

        }

        return rootView
    }

}