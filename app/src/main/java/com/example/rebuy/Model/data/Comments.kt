package com.example.rebuy.Model.data

data class Comments(
    val id: Int,
    val body: String,
    val postId: Int,
    val user: CommentsUsers
)

