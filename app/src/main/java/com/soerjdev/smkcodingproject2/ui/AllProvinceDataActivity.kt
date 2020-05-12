package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.main.activity_all_province_data.*

class AllProvinceDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_province_data)

        initView()
    }

    private fun initView() {
        setSupportActionBar(tbAllProvinceData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()

        return true
    }
}
