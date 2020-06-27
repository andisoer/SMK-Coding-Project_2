package com.soerjdev.smkcodingproject2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.database.model.GlobalCases
import com.soerjdev.smkcodingproject2.model.globalcases.GlobalCasesItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_country_data.*
import java.text.NumberFormat
import java.util.*

class AllCountryListAdapter(private val context: Context) :
    RecyclerView.Adapter<AllCountryListAdapter.ViewHolder> (){

    private var listGlobalCases = emptyList<GlobalCases>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_country_data,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return listGlobalCases.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listGlobalCases[position])
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: GlobalCases){

            tvNameItemCountryData.text = item.combinedKey
            tvPositifItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                            format(item.confirmed)
            tvRecoveredItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                                format(item.recovered)
            tvDeathItemCountryData.text = NumberFormat.getInstance(Locale.getDefault()).
                                                format(item.deaths)

        }
    }

    internal fun setGlobalCases(globalCases: List<GlobalCases>){
        this.listGlobalCases = globalCases
        notifyDataSetChanged()
    }
}