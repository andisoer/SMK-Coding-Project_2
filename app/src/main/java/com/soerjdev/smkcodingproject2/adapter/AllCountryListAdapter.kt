package com.soerjdev.smkcodingproject2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.model.globalcases.GlobalCasesItem
import com.soerjdev.smkcodingproject2.model.globaldata.GlobalDataItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_country_data.*
import java.text.NumberFormat
import java.util.*

class AllCountryListAdapter(private val context: Context,
                            private val listCountryDataActivity: List<GlobalCasesItem>) :
    RecyclerView.Adapter<AllCountryListAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_country_data,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return listCountryDataActivity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listCountryDataActivity[position])
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: GlobalCasesItem){

            tvNameItemCountryData.text = item.combinedKey
            tvPositifItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                            format(item.confirmed)
            tvRecoveredItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                                format(item.recovered)
            tvDeathItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                                format(item.deaths)

        }
    }
}