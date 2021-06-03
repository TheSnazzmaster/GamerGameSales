package com.honorsmobileapps.mathers.gamergamesales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honorsmobileapps.mathers.gamergamesales.databinding.AddGameItemLayoutBinding
import com.honorsmobileapps.mathers.gamergamesales.databinding.HomeListItemLayoutBinding
import kotlinx.android.synthetic.main.add_game_item_layout.view.*

class AddGameAdapter (val gameSaleInfoList: List<GameSaleInfo>): RecyclerView.Adapter<AddGameViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddGameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AddGameItemLayoutBinding.inflate(layoutInflater, parent, false)
        return AddGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddGameViewHolder, position: Int) {
        val currentGameSaleInfo = gameSaleInfoList[position]
        holder.bindGameSaleInfo(currentGameSaleInfo)
        holder.itemView.addGameButton.setOnClickListener {
            if(onClickListener!=null){
                onClickListener!!.onClick(position)
                viewModel.addGame()
            }
        }
    }

    override fun getItemCount(): Int {
        return gameSaleInfoList.size
    }

    private var onClickListener: View.OnClickListener? = null
    fun setOnClickListener(onClickListener: View.OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int){

        }
    }
}