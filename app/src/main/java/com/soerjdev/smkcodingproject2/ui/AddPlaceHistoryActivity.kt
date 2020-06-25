package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_add_place_history.*

class AddPlaceHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place_history)

        initView()
    }

    private fun initView() {

        tbAddPlaceHistory.setNavigationOnClickListener { finish() }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
