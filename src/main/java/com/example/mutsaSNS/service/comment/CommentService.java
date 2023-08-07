package com.example.mutsaSNS.service.comment;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.comment.CommentRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.comment.request.CommentCreateRequestDto;
import com.example.mutsaSNS.dto.comment.request.CommentUpdateRequestDto;
import com.example.mutsaSNS.dto.comment.response.CommentCreateResponseDto;
import com.example.mutsaSNS.dto.comment.response.CommentDeleteResponseDto;
import com.example.mutsaSNS.dto.comment.response.CommentUpdateResponseDto;
import com.example.mutsaSNS.exception.ErrorCode;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponseDto createComment(final CommentCreateRequestDto createDto, final Long postId, final Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        Comment comment = commentRepository.save(createDto.toEntity(user, post));

        return new CommentCreateResponseDto(comment);
    }

    @Transactional
    public CommentUpdateResponseDto updateComment(final CommentUpdateRequestDto updateDto, final Long commentId, final Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_COMMENT, NOT_FOUND_COMMENT.getMessage()));

        if (comment.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_COMMENT_USER, NOT_MATCH_COMMENT_USER.getMessage());
        }

        comment.updateComment(updateDto);

        return new CommentUpdateResponseDto(comment);
    }

    @Transactional
    public CommentDeleteResponseDto deleteComment(final Long commentId, final Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_COMMENT, NOT_FOUND_COMMENT.getMessage()));

        if (comment.getUser().getId() != userId) {
            throw new MutsaSnsAppException(NOT_MATCH_COMMENT_USER, NOT_MATCH_COMMENT_USER.getMessage());
        }

        commentRepository.delete(comment);

        return new CommentDeleteResponseDto(comment);
    }
}
