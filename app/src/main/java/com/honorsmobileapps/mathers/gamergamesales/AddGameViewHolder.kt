package com.honorsmobileapps.mathers.gamergamesales

import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.AddGameItemLayoutBinding

class AddGameViewHolder(val binding: AddGameItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo, addGameClickListener: (GameSaleInfo) -> Unit, openInternetClickListener: (GameSaleInfo) -> Unit){
        currentGameSaleInfo = gameSaleInfo
        binding.titleTextView2.text=currentGameSaleInfo.name
        binding.addGameButton.setOnClickListener{addGameClickListener(gameSaleInfo)}
        binding.addGameInternetButton.setOnClickListener{openInternetClickListener(gameSaleInfo)}
    }
}