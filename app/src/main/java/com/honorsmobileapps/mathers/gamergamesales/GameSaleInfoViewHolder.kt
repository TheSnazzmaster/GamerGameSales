package com.honorsmobileapps.mathers.gamergamesales

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding

class GameSaleInfoViewHolder(val binding: HomeListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo, removeGameClickListener: (GameSaleInfo) -> Unit, openInternetClickListener: (GameSaleInfo) -> Unit){
        currentGameSaleInfo = gameSaleInfo
        binding.titleTextView.text = currentGameSaleInfo.name
        binding.deleteButton.setOnClickListener{removeGameClickListener(gameSaleInfo)}
        binding.homeInternetButton.setOnClickListener{openInternetClickListener(gameSaleInfo)}
    }
}