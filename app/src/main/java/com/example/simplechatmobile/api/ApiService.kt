package com.example.simplechatmobile.api

import com.example.simplechatmobile.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/accounts/login/")
    fun login(@Body body: Map<String, String>): Call<TokenResponse>

    @POST("api/accounts/register/")
    fun register(@Body body: RegisterRequest): Call<Void>

    @GET("api/chat/chats/")
    fun getChats(): Call<List<Chat>>

    @GET("api/chat/messages/{chatId}/")
    fun getMessages(@Path("chatId") chatId: Int): Call<List<Message>>

    @POST("api/chat/message/send/")
    fun sendMessage(@Body body: MessageSendRequest): Call<Message>
}