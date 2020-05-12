package com.soerjdev.smkcodingproject2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.main.activity_all_country_data.*

class AllCountryDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_country_data)

        initView()
    }

    private fun initView() {
        setSupportActionBar(tbAllCountryData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onNavigateUp(): Boolean {
        finish()

        return true
    }
}
