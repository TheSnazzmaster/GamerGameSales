package com.honorsmobileapps.mathers.gamergamesales

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameSaleInfoViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    private lateinit var currentGameSaleInfo: GameSaleInfo
    private val gameTitle: TextView = itemView.findViewById(R.id.titleTextView)
    fun bindGameSaleInfo(gameSaleInfo: GameSaleInfo){
        currentGameSaleInfo = gameSaleInfo
        gameTitle.text = currentGameSaleInfo.name
    }
}