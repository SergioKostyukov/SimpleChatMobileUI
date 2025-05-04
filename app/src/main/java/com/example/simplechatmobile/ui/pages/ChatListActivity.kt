package com.example.simplechatmobile.ui.pages

import android.content.Intent
import android.os.Bundle
import com.example.simplechatmobile.ui.adapters.ChatAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplechatmobile.R
import com.example.simplechatmobile.api.ApiClient
import com.example.simplechatmobile.api.ApiService
import com.example.simplechatmobile.model.Chat
import com.example.simplechatmobile.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val token = SessionManager(this).authToken ?: return
        val api = ApiClient.getClient(token).create(ApiService::class.java)

        val listView = findViewById<ListView>(R.id.listViewChats)

        api.getChats().enqueue(object : Callback<List<Chat>> {
            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                if (response.isSuccessful) {
                    val chats = response.body() ?: emptyList()
                    val adapter = ChatAdapter(this@ChatListActivity, chats)
                    listView.adapter = adapter
                    listView.setOnItemClickListener { _, _, position, _ ->
                        val chat = chats[position]
                        val intent = Intent(this@ChatListActivity, ChatActivity::class.java)
                        intent.putExtra("chatId", chat.id)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@ChatListActivity, "Не вдалося завантажити чати", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                Toast.makeText(this@ChatListActivity, "Помилка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}