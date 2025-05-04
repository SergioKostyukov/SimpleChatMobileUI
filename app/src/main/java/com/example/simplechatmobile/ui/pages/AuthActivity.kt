package com.example.simplechatmobile.ui.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplechatmobile.R
import com.example.simplechatmobile.api.ApiClient
import com.example.simplechatmobile.api.ApiService
import com.example.simplechatmobile.model.TokenResponse
import com.example.simplechatmobile.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AuthActivity", "onCreate started")
        setContentView(R.layout.activity_auth)
        Log.d("AuthActivity", "layout loaded")

        val emailInput = findViewById<EditText>(R.id.editTextEmail)
        val passwordInput = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val api = ApiClient.getClient().create(ApiService::class.java)
            val body = mapOf("email" to email, "password" to password)
            api.login(body).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        SessionManager(this@AuthActivity).authToken = token
                        startActivity(Intent(this@AuthActivity, ChatListActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@AuthActivity, "Помилка входу", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.d("AuthActivity", "${t.message}")

                    Toast.makeText(this@AuthActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}