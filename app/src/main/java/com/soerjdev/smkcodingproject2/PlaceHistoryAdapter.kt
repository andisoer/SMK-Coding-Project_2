package com.soerjdev.smkcodingproject2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import com.soerjdev.smkcodingproject2.ui.PlaceHistoryListActivity
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_place_history.*

class PlaceHistoryAdapter(
    private val context: Context,
    private val listPlaceHistory: List<PlaceHistory>
) :
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

            val database = FirebaseDatabase.getInstance()
            val auth = FirebaseAuth.getInstance()
            val databaseRef = database.getReference("place_history")

            ivMoreItemPlaceHistory.setOnClickListener {
                val popupMenu = PopupMenu(containerView.context, ivMoreItemPlaceHistory)
                popupMenu.inflate(R.menu.menu_list_place_history)
                popupMenu.setOnMenuItemClickListener (PopupMenu.OnMenuItemClickListener {
                    return@OnMenuItemClickListener when (it.itemId){
                        R.id.menuUpdatePlaceHistory -> {
                            Log.d("PlaceHistoryAdapter", "update : "+item.uid)
                            true
                        }
                        R.id.menuDeletePlaceHistory -> {
                            databaseRef.child(auth.uid!!).child(item.uid).removeValue()
                                .addOnSuccessListener {
                                    showToast(containerView.context, "Berhasil Hapus Riwayat Perjalanan")
                                }
                            true
                        }
                        else -> false
                    }
                })
                popupMenu.show()
            }
        }
    }

}