package com.example.bbs_mongo.feed.controller

import com.example.bbs_mongo.feed.model.Feed
import com.example.bbs_mongo.feed.model.FeedRepository
import com.example.bbs_mongo.user.model.UserRepository
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/feed")
class FeedController(
        val feedRepository: FeedRepository,
        val userRepository: UserRepository
) {

    @GetMapping("/{id}")
    fun getFeed(@PathVariable id: UUID): Feed {
        feedRepository.findById(id).let { if (it.isPresent) return it.get() else throw ResponseStatusException(HttpStatus.NOT_FOUND, "feed not found") }
    }

    @GetMapping("")
    fun getFeeds(
            @QuerydslPredicate(root = Feed::class) predicate: Predicate,
            @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<Feed> {
        return feedRepository.findAll(predicate, pageable)
    }

    data class PostFeedReq(
            val title: String,
            val content: String,
            val userId: UUID,
    )
    @PostMapping("")
    fun postFeed(
            @RequestBody postFeedReq: PostFeedReq
    ): Feed {
        val user = userRepository.findById(postFeedReq.userId)
        if (user.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "userId not found")

        val feed = Feed(UUID.randomUUID(), postFeedReq.title, postFeedReq.content, user.get())
        return feedRepository.save(feed)
    }
}