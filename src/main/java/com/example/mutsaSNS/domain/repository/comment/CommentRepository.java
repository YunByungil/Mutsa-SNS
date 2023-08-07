package com.example.mutsaSNS.domain.repository.comment;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
