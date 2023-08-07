package com.example.mutsaSNS.domain.repository.like;

import com.example.mutsaSNS.domain.entity.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
