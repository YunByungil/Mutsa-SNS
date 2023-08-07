package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findAllByPostId(@Param("postId") Long postId);
}
