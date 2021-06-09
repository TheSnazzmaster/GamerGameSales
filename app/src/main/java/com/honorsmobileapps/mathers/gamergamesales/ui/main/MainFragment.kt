package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfo
import com.honorsmobileapps.mathers.gamergamesales.GameSaleInfoAdapter
import com.honorsmobileapps.mathers.gamergamesales.R
import com.honorsmobileapps.mathers.gamergamesales.databinding.MainFragmentBinding
import kotlin.concurrent.thread

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

        Log.i("help","load")

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
            Log.i("help",viewModel.getSavedGameList().toString())
        }

        binding.createDummyButton.setOnClickListener {
            viewModel.rawAddGame(GameSaleInfo("Celeste",0.0,"https://www.microsoft.com/en-us/p/celeste/bwmql2rpwbhb","",false))
            mAdapter.notifyDataSetChanged()
            mAdapter = GameSaleInfoAdapter(viewModel.getSavedGameList()!!,removeGameClickListener,openWebsiteClickListener)
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }

        viewModel.gameSaleInfoList.observe(viewLifecycleOwner){newList->
            mAdapter.notifyDataSetChanged()
            mAdapter = GameSaleInfoAdapter(viewModel.getSavedGameList()!!,removeGameClickListener,openWebsiteClickListener)
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            viewModel.updateGameSaleInfoPrices()
            Log.i("help", "save")
            Log.i("help","real"+viewModel.getSavedGameList().toString())

            var thing = mutableListOf(GameSaleInfo("Celeste",0.0,"https://www.microsoft.com/en-us/p/celeste/bwmql2rpwbhb","",false))
            viewModel.updatePrices(thing)
            Log.i("help",thing.toString())
        }

        viewModel.updateRecyclerView.observe(viewLifecycleOwner){
            if(it){
                viewModel.resetUpdateVariable()
                mAdapter.notifyDataSetChanged()
                mAdapter = GameSaleInfoAdapter(viewModel.getSavedGameList()!!,removeGameClickListener,openWebsiteClickListener)
                binding.RecyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
                Toast.makeText(activity,"Updated!",Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
