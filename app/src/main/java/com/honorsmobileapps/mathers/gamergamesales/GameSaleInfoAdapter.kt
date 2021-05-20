package com.honorsmobileapps.mathers.gamergamesales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding

class GameSaleInfoAdapter (val gameSaleInfoList: List<GameSaleInfo>): RecyclerView.Adapter<GameSaleInfoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameSaleInfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return GameSaleInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameSaleInfoViewHolder, position: Int) {
        val currentGameSaleInfo = gameSaleInfoList[position]
        holder.bindGameSaleInfo(currentGameSaleInfo)
    }

    override fun getItemCount(): Int {
        return gameSaleInfoList.size
    }
}