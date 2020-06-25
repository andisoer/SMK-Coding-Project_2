package com.soerjdev.smkcodingproject2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_place_history_list.*

class PlaceHistoryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_history_list)

        initView()
    }

    private fun initView() {

        tbListPlaceHistory.setNavigationOnClickListener { finish() }

        fabAddPlaceHistory.setOnClickListener {
            startActivity(Intent(this, AddPlaceHistoryActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
