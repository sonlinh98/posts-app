package com.ss.app.repository;

import com.ss.app.domain.Posts;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Posts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostsRepository extends MongoRepository<Posts, String> {
}
