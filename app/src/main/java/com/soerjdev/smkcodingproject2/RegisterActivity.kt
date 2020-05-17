package com.soerjdev.smkcodingproject2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var sharedPrefConfig : SharedPrefConfig
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPrefConfig = SharedPrefConfig

        sharedPreferences = getSharedPreferences(sharedPrefConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        initView()
    }

    private fun initView() {
        btnRegister.setOnClickListener { validateForm() }
    }

    private fun validateForm() {
        val fullName = tieNameRegister.text.toString().trim()
        val email = tieEmailRegister.text.toString().trim()
        val password = tiePasswordRegister.text.toString().trim()

        when{
            fullName.isEmpty() -> tieNameRegister.error = "Isi nama lengkap anda !"
            email.isEmpty() -> tieEmailRegister.error = "Isi email anda !"
            password.isEmpty() -> tiePasswordRegister.error = "Isi kata sandi anda !"
            else -> createPrefData(fullName, email, password)
        }
    }

    private fun createPrefData(
        fullName: String,
        email: String,
        password: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(sharedPrefConfig.TAG_FULLNAME, fullName)
        editor.putString(sharedPrefConfig.TAG_EMAIL, email)
        editor.putString(sharedPrefConfig.TAG_PASSWORD, password)
        editor.putBoolean(sharedPrefConfig.TAG_IS_LOGIN, true)
        editor.apply()

        showToast(this, "Berhasil mendaftar !")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
