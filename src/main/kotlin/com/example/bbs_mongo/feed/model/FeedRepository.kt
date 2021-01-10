package com.example.bbs_mongo.feed.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FeedRepository : MongoRepository<Feed, UUID>, QuerydslPredicateExecutor<Feed> {}