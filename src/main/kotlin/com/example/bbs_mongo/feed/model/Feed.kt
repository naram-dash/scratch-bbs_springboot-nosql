package com.example.bbs_mongo.feed.model

import com.example.bbs_mongo.user.model.User
import com.querydsl.core.annotations.QueryEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "feed")
data class Feed(
        @Id
        val id: UUID,

        val title: String,
        val content: String,

        val writer: User,
)