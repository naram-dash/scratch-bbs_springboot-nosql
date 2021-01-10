package com.example.bbs_mongo.user.controller

import com.example.bbs_mongo.user.model.User
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
@RequestMapping("/user")
class UserController(
        val userRepository: UserRepository
) {

    data class PostUserReq(
            val username: String,
            val password: String,
    )

    @PostMapping("")
    fun postUser(@RequestBody postUserReq: PostUserReq): User {
        val user = User(UUID.randomUUID(), postUserReq.username, postUserReq.password)
        return userRepository.save(user)
    }

    @GetMapping("")
    fun getUsers(
            @QuerydslPredicate(root = User::class) predicate: Predicate,
            @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<User> {
        return userRepository.findAll(predicate, pageable)
    }

    @GetMapping("/{id}")
    fun getUsers(
            @PathVariable id: UUID
    ): User {
        return userRepository.findById(id).let { if (it.isPresent) it.get() else throw ResponseStatusException(HttpStatus.NOT_FOUND, "user not found") }
    }
}