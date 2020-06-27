package com.soerjdev.smkcodingproject2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.soerjdev.smkcodingproject2.PlaceHistoryAdapter
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.interfaces.PlaceHistoryInterface
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_place_history_list.*

class PlaceHistoryListActivity : AppCompatActivity(), PlaceHistoryInterface {

    private lateinit var databaseReference: DatabaseReference
    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var listPlaceHistory: MutableList<PlaceHistory> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_history_list)

        initView()
    }

    private fun initView() {

        databaseReference = database.getReference("place_history")

        listPlaceHistory = ArrayList()

        tbListPlaceHistory.setNavigationOnClickListener { finish() }

        fabAddPlaceHistory.setOnClickListener {
            startActivity(Intent(this, AddPlaceHistoryActivity::class.java))
        }

        getPlaceHistory()

    }

    private fun getPlaceHistory() {
        pbLoadListPlaceHistory.visibility = View.VISIBLE
        databaseReference.child(auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listPlaceHistory.clear()
                    for (data in snapshot.children){
                        val placeHistory = data.getValue(PlaceHistory::class.java)

                        placeHistory?.uid = data.key!!

                        listPlaceHistory.add(placeHistory!!)

                    }

                    rvListPlaceHistory.layoutManager = LinearLayoutManager(this@PlaceHistoryListActivity)
                    rvListPlaceHistory.adapter = PlaceHistoryAdapter(
                        this@PlaceHistoryListActivity,
                        listPlaceHistory
                    )

                    pbLoadListPlaceHistory.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    override fun deletePlaceHistory(uid: String) {

    }

    override fun updatePlaceHistory(placeHistory: PlaceHistory) {

    }
}
