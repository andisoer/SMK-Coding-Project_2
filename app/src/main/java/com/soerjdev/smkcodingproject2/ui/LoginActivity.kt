package com.soerjdev.smkcodingproject2.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soerjdev.smkcodingproject2.MainActivity
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.utils.SharedPrefUtil
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences(SharedPrefUtil.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean(SharedPrefUtil.TAG_IS_LOGIN, false)

        if (isLogin){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        initView()
    }

    private fun initView() {

        btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            validateForm()
        }

    }

    private fun validateForm() {
        val email = tieEmailLogin.text.toString().trim()
        val password = tiePasswordLogin.text.toString().trim()

        when {
            email.isEmpty() -> tieEmailLogin.error = "Isi email anda !"
            password.isEmpty() -> tiePasswordLogin.error = "Isi kata sandi anda !"
            else -> checkPrefData(email, password)
        }
    }

    private fun checkPrefData(email : String, password : String) {
        val emailPref = sharedPreferences.getString(SharedPrefUtil.TAG_EMAIL, null)
        val passwordPref = sharedPreferences.getString(SharedPrefUtil.TAG_PASSWORD, null)

        if(emailPref.equals(email) && passwordPref.equals(password)){

            val editor = sharedPreferences.edit()
            editor.putBoolean(SharedPrefUtil.TAG_IS_LOGIN, true)
            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            showToast(
                this,
                "User tidak ditemukan, silahkan mendaftar"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
