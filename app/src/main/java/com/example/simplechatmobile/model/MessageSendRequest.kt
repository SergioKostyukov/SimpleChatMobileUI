package com.example.simplechatmobile.model

data class MessageSendRequest(
    val chat: Int,
    val sender: Int,
    val content: String
)