package com.honorsmobileapps.mathers.gamergamesales

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class AddGameFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val openWebsiteClickListener: (GameSaleInfo) -> Unit = { gameSaleInfo: GameSaleInfo ->
            if(gameSaleInfo.url!="") {
                val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gameSaleInfo.url))
                this.context?.startActivity(websiteIntent)
            }
        }


        var mAdapter = AddGameAdapter(viewModel.getOnlineGameList()!!,openWebsiteClickListener) { gameSaleInfo: GameSaleInfo ->
            viewModel.addGame(gameSaleInfo)
        }
        binding.RecyclerView.adapter = mAdapter

        binding.searchButton.setOnClickListener {
            viewModel.search(binding.searchEditText.text.toString())
            mAdapter.notifyDataSetChanged()

        }

        val addGameClickListener: (GameSaleInfo) -> Unit = { gameSaleInfo: GameSaleInfo ->
            viewModel.addGame(gameSaleInfo)
            mAdapter.notifyDataSetChanged()
        }

        viewModel.onlineGameList.observe(viewLifecycleOwner){newList->
            mAdapter.notifyDataSetChanged()
            mAdapter = AddGameAdapter(viewModel.getOnlineGameList()!!,addGameClickListener,openWebsiteClickListener)
            binding.RecyclerView.adapter = mAdapter
            mAdapter.notifyDataSetChanged()

        }

        viewModel.toastButFromViewModel.observe(viewLifecycleOwner){newToastMessage->
            if(newToastMessage!="")
                Toast.makeText(activity,newToastMessage,Toast.LENGTH_SHORT).show()
        }

        binding.toHomeImageButton.setOnClickListener {
            rootView.findNavController().popBackStack()
        }


        return rootView
    }
}