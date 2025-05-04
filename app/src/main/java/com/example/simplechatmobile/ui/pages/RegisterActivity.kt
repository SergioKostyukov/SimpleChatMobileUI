package com.example.simplechatmobile.ui.pages

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplechatmobile.R
import com.example.simplechatmobile.api.ApiClient
import com.example.simplechatmobile.api.ApiService
import com.example.simplechatmobile.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailInput = findViewById<EditText>(R.id.editTextEmail)
        val usernameInput = findViewById<EditText>(R.id.editTextUsername)
        val passwordInput = findViewById<EditText>(R.id.editTextPassword)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            val api = ApiClient.getClient().create(ApiService::class.java)
            val body = RegisterRequest(email, username, password)

            api.register(body).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Реєстрація успішна", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Помилка реєстрації", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}