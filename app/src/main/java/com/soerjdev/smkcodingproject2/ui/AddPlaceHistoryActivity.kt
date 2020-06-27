package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_add_place_history.*

class AddPlaceHistoryActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var databaseRef: DatabaseReference
    private var OPERATION_TYPE = ""
    private var updatedDataUid = ""

    private var TAG = AddPlaceHistoryActivity::class.java.name

    companion object {
        public val TAG_OPERATION_TYPE = "TYPE"
        public val PLACE_NAME = "placeName"
        public val PLACE_ADDRESS = "placeAddress"
        public val PLACE_DATE = "placeDate"
        public val PLACE_TYPE = "placeType"
        public val PLACE_UID = "placeUid"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place_history)

        initView()
    }

    private fun initView() {

        val intent = intent

        OPERATION_TYPE = intent.getStringExtra(TAG_OPERATION_TYPE)!!

        if(OPERATION_TYPE == "Update"){
            val placeName = intent.getStringExtra(PLACE_NAME)
            val placeAddress = intent.getStringExtra(PLACE_ADDRESS)
            val placeType = intent.getStringExtra(PLACE_TYPE)
            val placeDate = intent.getStringExtra(PLACE_DATE)
            updatedDataUid = intent.getStringExtra(PLACE_UID)!!

            setToTextInput(placeName, placeDate, placeAddress, placeType)
        }

        databaseRef = database.getReference("place_history")

        tbAddPlaceHistory.setNavigationOnClickListener { finish() }

        if(OPERATION_TYPE == "Update"){
            btnAddPlaceHistory.text = "Update Riwayat"
            tbAddPlaceHistory.title = "Update Riwayat Perjalanan"
        }

        btnAddPlaceHistory.setOnClickListener {
            checkForm()
        }

    }

    private fun setToTextInput(placeName: String?, placeDate: String?, placeAddress: String?, placeType: String?) {
        tieNameAddPlaceHistory.setText(placeName)
        tieAddressAddPlaceHistory.setText(placeAddress)
        tieCategoryAddPlaceHistory.setText(placeType)
        tieDateAddPlaceHistory.setText(placeDate)
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
            else -> when(OPERATION_TYPE){
                "Input" -> insertData(placeName, placeAddress, placeType, placeDate)
                "Update" -> updateData(placeName, placeAddress, placeType, placeDate)
            }
        }
    }

    private fun updateData(placeName: String, placeAddress: String, placeType: String, placeDate: String) {
        val placeHistory = PlaceHistory(placeName, placeAddress, placeType, placeDate, "")
        databaseRef.child(auth.uid!!).child(updatedDataUid).setValue(placeHistory)
            .addOnCompleteListener {
                showToast(this, "Berhasil update riwayat perjalanan !")
                finish()
            }
            .addOnFailureListener {
                Log.e(TAG, "Error updating : "+it.message)
            }
    }

    private fun insertData(placeName: String, placeAddress: String, placeType: String, placeDate: String) {
        val placeHistory = PlaceHistory(placeName, placeAddress, placeType, placeDate, "")
        databaseRef.child(auth.uid!!).push().setValue(placeHistory)
            .addOnCompleteListener {
                showToast(this, "Berhasil menambah riwayat perjalanan !")
                finish()
            }
            .addOnFailureListener {
                Log.e(TAG, "Error inserting : "+it.message)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
