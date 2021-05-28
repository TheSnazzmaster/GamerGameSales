package com.honorsmobileapps.mathers.gamergamesales

import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.AddGameItemLayoutBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding

class AddGameViewHolder(val binding: AddGameItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo){
        currentGameSaleInfo = gameSaleInfo
    }
}