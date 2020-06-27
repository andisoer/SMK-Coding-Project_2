package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_add_place_history.*

class AddPlaceHistoryActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place_history)

        initView()
    }

    private fun initView() {

        databaseRef = database.getReference("place_history")

        tbAddPlaceHistory.setNavigationOnClickListener { finish() }

        btnAddPlaceHistory.setOnClickListener {
            checkForm()
        }

    }

    private fun checkForm() {
        val placeName = tieNameAddPlaceHistory.text.toString()
        val placeAddress = tieAddressAddPlaceHistory.text.toString()
        val placeType = tieCategoryAddPlaceHistory.text.toString()
        val placeDate = tieDateAddPlaceHistory.text.toString()

        when {
            placeName.isEmpty() -> tilNameAddPlaceHistory.error = "Isi nama tempat !"
            placeAddress.isEmpty() -> tilAddressAddPlaceHistory.error = "Isi alamat tempat !"
            placeType.isEmpty() -> tilCategoryAddPlaceHistory.error = "Isi jenis perjalanan !"
            placeDate.isEmpty() -> tilDateAddPlaceHistory.error = "Isi tanggal perjalanan !"
            else -> insertData(placeName, placeAddress, placeType, placeDate)
        }
    }

    private fun insertData(placeName: String, placeAddress: String, placeType: String, placeDate: String) {
        val placeHistory = PlaceHistory(placeName, placeAddress, placeType, placeDate, "")
        databaseRef.child(auth.uid!!).push().setValue(placeHistory)
            .addOnCompleteListener {

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
