package com.example.simplechatmobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.simplechatmobile.R
import com.example.simplechatmobile.model.Message

class MessageAdapter(private val context: Context, private val messages: List<Message>) : BaseAdapter() {
    override fun getCount(): Int = messages.size
    override fun getItem(position: Int): Any = messages[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        val message = getItem(position) as Message

        val senderNameTextView = view.findViewById<TextView>(R.id.textViewSenderName)
        val messageTextView = view.findViewById<TextView>(R.id.textViewMessage)

        senderNameTextView.text = message.sender_name
        messageTextView.text = message.content

        return view
    }
}
