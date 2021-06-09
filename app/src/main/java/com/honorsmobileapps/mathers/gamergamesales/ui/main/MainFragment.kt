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
        }

        viewModel.gameSaleInfoList.observe(viewLifecycleOwner){newList->
            mAdapter.notifyDataSetChanged()
            mAdapter = GameSaleInfoAdapter(viewModel.getSavedGameList()!!,removeGameClickListener,openWebsiteClickListener)
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            Log.i("help", "save")
        }

        binding.saveButton.setOnClickListener{
            saveGamesToPreferences(viewModel.getSavedGameList()!!)
        }
        binding.loadButton.setOnClickListener {
            viewModel.assignGameList(getGamesFromPreferences())
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //this saving stuff was learned from https://www.youtube.com/watch?v=8zPkbV4INGA&ab_channel=ResoCoderResoCoder
    //well i say "learned" but i really mean "yoinked", but i think i understand it well enough to use it
    fun saveGamesToPreferences(games: List<GameSaleInfo>){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        val jsonString = Gson().toJson(games)
        prefEditor.putString("games",jsonString).apply()
    }
    fun getGamesFromPreferences(): MutableList<GameSaleInfo>{
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = preferences.getString("games",null)

        return if (jsonString!=null)
            Gson().fromJson(jsonString,object: TypeToken<MutableList<GameSaleInfo>>(){}.type)
        else
            mutableListOf()
    }
}
