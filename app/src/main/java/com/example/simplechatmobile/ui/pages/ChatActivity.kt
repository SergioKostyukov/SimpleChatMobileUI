package com.example.simplechatmobile.ui.pages

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplechatmobile.R
import com.example.simplechatmobile.api.ApiClient
import com.example.simplechatmobile.api.ApiService
import com.example.simplechatmobile.model.Message
import com.example.simplechatmobile.model.MessageSendRequest
import com.example.simplechatmobile.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ArrayAdapter

class ChatActivity : AppCompatActivity() {
    private var chatId: Int = -1
    private var senderId: Int = 1 // змінити на ID поточного користувача

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.getIntExtra("chatId", -1)

        val messageInput = findViewById<EditText>(R.id.editTextMessage)
        val sendButton = findViewById<Button>(R.id.buttonSend)
        val listView = findViewById<ListView>(R.id.listViewMessages)

        val token = SessionManager(this).authToken ?: return
        val api = ApiClient.getClient(token).create(ApiService::class.java)

        fun loadMessages() {
            api.getMessages(chatId).enqueue(object : Callback<List<Message>> {
                override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                    if (response.isSuccessful) {
                        val messages = response.body() ?: emptyList()
                        val adapter = ArrayAdapter(this@ChatActivity, android.R.layout.simple_list_item_1, messages.map { it.content })
                        listView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    Toast.makeText(this@ChatActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        sendButton.setOnClickListener {
            val content = messageInput.text.toString()
            val message = MessageSendRequest(chatId, senderId, content)
            api.sendMessage(message).enqueue(object : Callback<Message> {
                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    if (response.isSuccessful) {
                        messageInput.setText("")
                        loadMessages()
                    } else {
                        Toast.makeText(this@ChatActivity, "Помилка надсилання", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Toast.makeText(this@ChatActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        loadMessages()
    }
}