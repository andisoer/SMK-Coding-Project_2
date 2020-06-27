package com.soerjdev.smkcodingproject2.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.soerjdev.smkcodingproject2.MainActivity
import com.soerjdev.smkcodingproject2.R
import com.soerjdev.smkcodingproject2.utils.SharedPrefUtils
import com.soerjdev.smkcodingproject2.utils.showToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    lateinit var gso : GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth : FirebaseAuth

    lateinit var callbackManager: CallbackManager

    val TAG = LoginActivity::class.java.name

    companion object {
        const val REQ_CODE_GOOGLE_AUTH = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /* init Google Sign In */
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        /* init Facebook Sign I*/

        callbackManager = CallbackManager.Factory.create()

        sharedPreferences = getSharedPreferences(SharedPrefUtils.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean(SharedPrefUtils.TAG_IS_LOGIN, false)

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

        btnSignInGoogle.setOnClickListener {
            signInWithGoogle()
        }

        btnLoginFacebook2.setOnClickListener {
            btnLoginFacebook.performClick()
        }

        btnLoginFacebook.setOnClickListener {

        }

        btnLoginFacebook.setPermissions("email","public_profile")
        btnLoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "fb auth onSucess: $result")
                signInWithFacebook(result!!.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "fb auth onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.e(TAG, "fb auth onError : "+error?.message)
            }
        })

    }

    private fun signInWithFacebook(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d(TAG, "fb auth complete")
                    Log.d(TAG, "User : "+auth.currentUser?.displayName)
                    intentToMain()
                }else{
                    Log.e(TAG, "fb credential exception : "+task.exception)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE_GOOGLE_AUTH)
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
        val emailPref = sharedPreferences.getString(SharedPrefUtils.TAG_EMAIL, null)
        val passwordPref = sharedPreferences.getString(SharedPrefUtils.TAG_PASSWORD, null)

        if(emailPref.equals(email) && passwordPref.equals(password)){

            val editor = sharedPreferences.edit()
            editor.putBoolean(SharedPrefUtils.TAG_IS_LOGIN, true)
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

    private fun authWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
//                    Log.d(TAG, "Auth complete")
//                    Log.d(TAG, "User : "+auth.currentUser?.displayName)
                    intentToMain()
                }else{
                    Log.e(TAG, "Failed to authenticate : "+task.exception)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_CODE_GOOGLE_AUTH){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "account signed : "+account?.id)
                authWithGoogle(account?.idToken)
            }catch (e: ApiException) {
                Log.e(TAG, "error on sign in : " + e.message)
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            intentToMain()
        }
    }

    private fun intentToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
