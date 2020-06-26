package com.soerjdev.smkcodingproject2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_place_history.*

class PlaceHistoryAdapter (private val context: Context,
                           private val listPlaceHistory: List<PlaceHistory>) :
        RecyclerView.Adapter<PlaceHistoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
      LayoutInflater.from(context).inflate(R.layout.layout_item_place_history, parent, false)
    )

    override fun getItemCount(): Int {
        return listPlaceHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listPlaceHistory[position])
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: PlaceHistory){
            tvPlaceNameItemPlaceHistory.text = item.placeName
            tvAddresItemPlaceHistory.text = item.placeAddres
        }
    }

}