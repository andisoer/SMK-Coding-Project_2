package com.soerjdev.smkcodingproject2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import com.soerjdev.smkcodingproject2.model.provdata.Data
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_province_data.*
import java.text.NumberFormat
import java.util.*

class ProvinsiListAdapter (private val context: Context) :
    RecyclerView.Adapter<ProvinsiListAdapter.ViewHolder> (){

    private var listProvinsiCases = emptyList<ProvinsiCases>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_item_province_data,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return listProvinsiCases.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listProvinsiCases[position])
    }

    class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: ProvinsiCases){

            tvNameItemProvinceData.text = item.key
            tvPositifItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahKasus)
            tvRecoveredItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahSembuh)
            tvDeathItemProvinceData.text = NumberFormat.getInstance(Locale.getDefault()).
            format(item.jumlahMeninggal)
        }
    }

    internal fun setProvinsiCases(provinsiCases: List<ProvinsiCases>){
        this.listProvinsiCases = provinsiCases
        notifyDataSetChanged()
    }
}