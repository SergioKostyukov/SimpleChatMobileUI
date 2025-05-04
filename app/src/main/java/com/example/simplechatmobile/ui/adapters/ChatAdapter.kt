package com.example.simplechatmobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.simplechatmobile.R
import com.example.simplechatmobile.model.Chat

class ChatAdapter(
    context: Context,
    chats: List<Chat>
) : ArrayAdapter<Chat>(context, 0, chats) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_chat, parent, false)

        val chat = getItem(position)
        val nameTextView = view.findViewById<TextView>(R.id.textViewChatName)
        nameTextView.text = chat?.name ?: ""

        return view
    }
}
