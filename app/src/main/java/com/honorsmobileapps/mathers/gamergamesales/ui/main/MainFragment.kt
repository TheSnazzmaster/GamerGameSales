package com.honorsmobileapps.mathers.gamergamesales.ui.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        if (isConnected()) { //learned from https://www.youtube.com/watch?v=MMcfdEzfdB4


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
            viewModel.addDummyGame()
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
        } else {
            val builder = activity?.let { AlertDialog.Builder(it) }
            builder?.setTitle("No Internet")
            builder?.setMessage(
                "This app needs the Internet to update prices and function. " +
                        "Please try again when you have an Internet Connection"
            )
            builder?.setPositiveButton("Ok"
            ) { _: DialogInterface, _: Int -> requireActivity().finish() }
            builder?.show()
        }

        return rootView
    }


    //this stuff about checking for connectivity was learned from
    // https://www.raywenderlich.com/6994782-android-networking-with-kotlin-tutorial-getting-started
    //same deal where I'm not sure how much is flexible and I don't want to change variable names purely for the sake of changing
    // but I'm actually trying to understand this
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnected(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
