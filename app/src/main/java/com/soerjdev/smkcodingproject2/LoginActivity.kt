package com.soerjdev.smkcodingproject2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPrefConfig : SharedPrefConfig
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPrefConfig = SharedPrefConfig

        sharedPreferences = getSharedPreferences(sharedPrefConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean(sharedPrefConfig.TAG_IS_LOGIN, false)

        if (isLogin){
            startActivity(Intent(this, MainActivity::class.java))
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
        val password = tiePasswordLogin.text.toString().toString()

        when {
            email.isEmpty() -> tieEmailLogin.error = "Isi email anda !"
            password.isEmpty() -> tiePasswordLogin.error = "Isi kata sandi anda !"
            else -> checkPrefData(email, password)
        }
    }

    private fun checkPrefData(email : String, password : String) {
        val emailPref = sharedPrefConfig.TAG_EMAIL
        val passwordPref = sharedPrefConfig.TAG_PASSWORD

        if(emailPref.equals(email) && passwordPref.equals(password)){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            showToast(this, "User tidak ditemukan, silahkan mendaftar")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
