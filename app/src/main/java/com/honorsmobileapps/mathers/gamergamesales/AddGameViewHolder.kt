package com.honorsmobileapps.mathers.gamergamesales

import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.AddGameItemLayoutBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding
import kotlinx.android.synthetic.main.add_game_item_layout.view.*

class AddGameViewHolder(val binding: AddGameItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo){
        currentGameSaleInfo = gameSaleInfo
        binding.titleTextView2.text=currentGameSaleInfo.name
    }
}