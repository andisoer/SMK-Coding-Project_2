package com.soerjdev.smkcodingproject2.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soerjdev.smkcodingproject2.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        containerNameProvinces.setOnClickListener {
            startActivity(Intent(context, AllProvinceDataActivity::class.java))
        }

        containerNameWorld.setOnClickListener {
            startActivity(Intent(context, AllCountryDataActivity::class.java))
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
