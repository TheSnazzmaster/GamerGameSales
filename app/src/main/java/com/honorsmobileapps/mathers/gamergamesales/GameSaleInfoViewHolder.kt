package com.honorsmobileapps.mathers.gamergamesales

import android.graphics.Color
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding
import com.squareup.picasso.Picasso

class GameSaleInfoViewHolder(val binding: HomeListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo, removeGameClickListener: (GameSaleInfo) -> Unit, openInternetClickListener: (GameSaleInfo) -> Unit){
        currentGameSaleInfo = gameSaleInfo
        binding.titleTextView.text = currentGameSaleInfo.name
        binding.deleteButton.setOnClickListener{removeGameClickListener(gameSaleInfo)}
        binding.homeInternetButton.setOnClickListener{openInternetClickListener(gameSaleInfo)}
        if(currentGameSaleInfo.imageUrl!="")
            Picasso.get().load(currentGameSaleInfo.imageUrl).into(binding.homeGameImageView)
        if(currentGameSaleInfo.isOnSale){
            binding.pricingTextView.text = "ON SALE - $${currentGameSaleInfo.price}"
            binding.pricingTextView.setTextColor(Color.RED)
        }
        else if(currentGameSaleInfo.price<0.0){
            binding.pricingTextView.text = "Pricing info not available"
            binding.pricingTextView.setTextColor(Color.parseColor("#dcdcdc"))
        }
        else if(currentGameSaleInfo.price==0.0){
            binding.pricingTextView.text = "Free"
            binding.pricingTextView.setTextColor(Color.parseColor("#dcdcdc"))
        }
        else{
            binding.pricingTextView.text = "Not on sale - $${currentGameSaleInfo.price}"
            binding.pricingTextView.setTextColor(Color.parseColor("#dcdcdc"))
        }
    }
}