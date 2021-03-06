package com.soerjdev.smkcodingproject2.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.soerjdev.smkcodingproject2.ui.PlaceHistoryListActivity
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.ui.LoginActivity
import com.soerjdev.smkcodingproject2.utils.SharedPrefUtils
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var sharedPreferences : SharedPreferences
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        auth = FirebaseAuth.getInstance()

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        containerAboutApps.setOnClickListener {
            showAboutDialog()
        }

        containerAboutMe.setOnClickListener {
            showAboutMeDialog()
        }

        containerPlaceHistory.setOnClickListener {
            activity?.startActivity(Intent(requireActivity(), PlaceHistoryListActivity::class.java))
        }

        if(auth.currentUser != null){
            setData(null, null, "auth")
        }else{
            getPrefData()
        }
    }

    private fun showAboutMeDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_about_me, null)

        val alertDialogBuilder = MaterialAlertDialogBuilder(activity)
            .setPositiveButton("Tutup") {dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        alertDialogBuilder.setView(view)
        alertDialogBuilder.show()
    }

    private fun showAboutDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_about_apps, null)

        val alertDialogBuilder = MaterialAlertDialogBuilder(activity)
            .setPositiveButton("Tutup") {dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        alertDialogBuilder.setView(view)
        alertDialogBuilder.show()

    }

    private fun showLogoutDialog() {
        val alertDialogBuilder = MaterialAlertDialogBuilder(activity)
            .setTitle("Logout")
            .setMessage("Anda ingin logout ?")
            .setNegativeButton("Tidak") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Ya") { _, _ ->
                if(auth.currentUser != null){
                    logout()
                }else {
                    clearPrefData()
                }
            }

        alertDialogBuilder.show()

    }

    private fun logout() {
        auth.signOut()
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    private fun getPrefData() {
        sharedPreferences = requireContext().getSharedPreferences(SharedPrefUtils.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val fullName = sharedPreferences.getString(SharedPrefUtils.TAG_FULLNAME, null)
        val email = sharedPreferences.getString(SharedPrefUtils.TAG_EMAIL, null)

        setData(fullName, email, "sharedPref")
    }

    private fun setData(fullName: String?, email: String?, typeAuth: String) {
        if(typeAuth == "auth"){
            tvNameUserProfile.text = auth.currentUser?.displayName
            tvEmailUserProfile.text = auth.currentUser?.email
        }else{
            tvNameUserProfile.text = email
            tvEmailUserProfile.text = fullName
        }

    }

    private fun clearPrefData() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(SharedPrefUtils.TAG_IS_LOGIN, false)
        editor.apply()

        showToast(requireContext(), "Berhasil keluar, silahkan login")
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
