package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_add_place_history.*

class AddPlaceHistoryActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var databaseRef: DatabaseReference
    private var OPERATION_TYPE = ""
    private var updatedDataUid = ""

    private lateinit var adapter: ArrayAdapter<CharSequence>

    private var categoryPlaceHistory = ""

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

        tbAddPlaceHistory.setNavigationOnClickListener { finish() }

        val intent = intent

        OPERATION_TYPE = intent.getStringExtra(TAG_OPERATION_TYPE)!!

        adapter = ArrayAdapter.createFromResource(
            this,
            R.array.place_history_type,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoryAddPlaceHistory.adapter = adapter

        if(OPERATION_TYPE == "Update"){
            val placeName = intent.getStringExtra(PLACE_NAME)
            val placeAddress = intent.getStringExtra(PLACE_ADDRESS)
            val placeType = intent.getStringExtra(PLACE_TYPE)
            val placeDate = intent.getStringExtra(PLACE_DATE)
            updatedDataUid = intent.getStringExtra(PLACE_UID)!!

            setToTextInput(placeName, placeDate, placeAddress, placeType)
        }

        databaseRef = database.getReference("place_history")

        spinnerCategoryAddPlaceHistory.onItemSelectedListener = this

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

        val spinnerPosition = adapter.getPosition(placeType)
        spinnerCategoryAddPlaceHistory.setSelection(spinnerPosition)

        tieDateAddPlaceHistory.setText(placeDate)
    }

    private fun checkForm() {
        val placeName = tieNameAddPlaceHistory.text.toString()
        val placeAddress = tieAddressAddPlaceHistory.text.toString()
        val placeDate = tieDateAddPlaceHistory.text.toString()

        when {
            placeName.isEmpty() -> tilNameAddPlaceHistory.error = "Isi nama tempat !"
            placeAddress.isEmpty() -> tilAddressAddPlaceHistory.error = "Isi alamat tempat !"
            categoryPlaceHistory.isEmpty() -> showToast(this,"Isi jenis perjalanan !")
            placeDate.isEmpty() -> tilDateAddPlaceHistory.error = "Isi tanggal perjalanan !"
            else -> when(OPERATION_TYPE){
                "Input" -> insertData(placeName, placeAddress, categoryPlaceHistory, placeDate)
                "Update" -> updateData(placeName, placeAddress, categoryPlaceHistory, placeDate)
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

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        categoryPlaceHistory = parent?.getItemAtPosition(position).toString()
    }
}
