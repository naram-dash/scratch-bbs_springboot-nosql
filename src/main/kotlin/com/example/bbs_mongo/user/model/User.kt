package com.example.bbs_mongo.user.model

import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "user")
data class User(
        @Id
        val id: UUID,

        val username: String,
        val password: String
)