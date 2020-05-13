package com.soerjdev.smkcodingproject2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soerjdev.smkcodingproject2.model.ModelTips
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_info.*

class TipsAdapter(private val context: Context, private val listTips: ArrayList<ModelTips>) :
    RecyclerView.Adapter<TipsAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.layout_item_info, parent, false)
    )

    override fun getItemCount(): Int {
        return listTips.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelTips = listTips[position]
        holder.bindItem(modelTips)

        holder.itemView.setOnClickListener{
            val expanded = modelTips.expandedView
            modelTips.expandedView = !expanded

            notifyItemChanged(position)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: ModelTips){

            val expanded = item.expandedView

            containerSubInfoItem.visibility = if (expanded) View.VISIBLE else View.GONE

            tvQuestionInfoItem.text = item.pertanyaan
            tvDescriptionTipsItem.text = item.jawaban
        }
    }

}