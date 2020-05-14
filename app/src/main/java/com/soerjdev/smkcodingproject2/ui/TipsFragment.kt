package com.soerjdev.smkcodingproject2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.adapter.TipsAdapter
import com.soerjdev.smkcodingproject2.model.ModelTips
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * A simple [Fragment] subclass.
 */
class TipsFragment : Fragment() {

    lateinit var listTips : ArrayList<ModelTips>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        addData()
        setRecylerView()
    }

    private fun addData() {
        listTips = ArrayList()
        val questions = resources.getStringArray(R.array.pertanyaan)
        val answer = resources.getStringArray(R.array.jawaban)

        for ( i in questions.indices){
            val modelTips = ModelTips()
            modelTips.pertanyaan = questions[i]
            modelTips.jawaban = answer[i]

            listTips.add(modelTips)
        }
    }

    private fun setRecylerView() {
        rvInfo.layoutManager = LinearLayoutManager(activity)
        rvInfo.adapter = TipsAdapter(
            activity!!,
            listTips
        )
        hideProgressBar()
    }

    private fun hideProgressBar() {
        pbLoadInfo.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
