package com.example.simplechatmobile.model

data class Message(
    val id: Int,
    val chat: Int,
    val sender: Int,
    val sender_name: String,
    val content: String,
    val timestamp: String
)
