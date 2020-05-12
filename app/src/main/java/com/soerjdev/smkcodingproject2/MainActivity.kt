package com.soerjdev.smkcodingproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.soerjdev.smkcodingproject2.ui.DashboardFragment
import com.soerjdev.smkcodingproject2.ui.GraphFragment
import com.soerjdev.smkcodingproject2.ui.InfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        bottomNavMain.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuDashboard -> {
                    loadFragment(DashboardFragment())
                }
                R.id.menuGraph -> {
                    loadFragment(GraphFragment())
                }
                R.id.menuInfo -> {
                    loadFragment(InfoFragment())
                } else -> false
            }
        }
        bottomNavMain.selectedItemId = R.id.menuDashboard

    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutMain, fragment)
            .commit()
        return true
    }
}
