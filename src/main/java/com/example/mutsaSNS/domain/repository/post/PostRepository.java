package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
