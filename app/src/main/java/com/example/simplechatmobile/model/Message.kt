package com.example.simplechatmobile.model

data class Message(
    val id: Int,
    val chat: Int,
    val sender: Int,
    val content: String,
    val timestamp: String
)
