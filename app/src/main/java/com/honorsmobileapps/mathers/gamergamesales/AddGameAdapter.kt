package com.honorsmobileapps.mathers.gamergamesales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.AddGameItemLayoutBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding

class AddGameAdapter (val gameSaleInfoList: List<GameSaleInfo>): RecyclerView.Adapter<AddGameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddGameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AddGameItemLayoutBinding.inflate(layoutInflater, parent, false)
        return AddGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddGameViewHolder, position: Int) {
        val currentGameSaleInfo = gameSaleInfoList[position]
        holder.bindGameSaleInfo(currentGameSaleInfo)
    }

    override fun getItemCount(): Int {
        return gameSaleInfoList.size
    }
}