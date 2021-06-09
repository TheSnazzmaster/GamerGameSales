package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfoAdapter
import com.honorsmobileapps.mathers.gamergamesales.R
import com.honorsmobileapps.mathers.gamergamesales.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        lateinit var mAdapter: GameSaleInfoAdapter
        val openWebsiteClickListener: (GameSaleInfo) -> Unit = { gameSaleInfo: GameSaleInfo ->
            if(gameSaleInfo.url!="") {
                val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gameSaleInfo.url))
                this.context?.startActivity(websiteIntent)
            }
        }

        val removeGameClickListener: (GameSaleInfo) -> Unit = { gameSaleInfo: GameSaleInfo ->
            viewModel.removeGame(gameSaleInfo)
            mAdapter.notifyDataSetChanged()
        }

        var recyclerViewList = viewModel.getSavedGameList()!!
        mAdapter = GameSaleInfoAdapter(recyclerViewList,removeGameClickListener,openWebsiteClickListener)
        binding.RecyclerView.adapter = mAdapter

        binding.toAddGameFragmentButton.setOnClickListener {
            rootView.findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAddGameFragment()
            )
        }

        viewModel.gameSaleInfoList.observe(viewLifecycleOwner){newList->
            mAdapter.notifyDataSetChanged()
            mAdapter = GameSaleInfoAdapter(viewModel.getSavedGameList()!!,removeGameClickListener,openWebsiteClickListener)
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
