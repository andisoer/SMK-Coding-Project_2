package com.soerjdev.smkcodingproject2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.model.provdata.Data
import com.soerjdev.smkcodingproject2.model.provinsidata.ProvinsiDataItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_province_data.*
import java.text.NumberFormat
import java.util.*

class ProvinsiListAdapter (private val context: Context,
                           private val listProvinsiData: List<Data>) :
    RecyclerView.Adapter<ProvinsiListAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_province_data,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return listProvinsiData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listProvinsiData[position])
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Data){

            tvNameItemProvinceData.text = item.key
            tvPositifItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahKasus)
            tvRecoveredItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahSembuh)
            tvDeathItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahMeninggal)
        }
    }
}