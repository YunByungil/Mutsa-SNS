package com.example.mutsaSNS.service.comment;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.comment.CommentRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.comment.request.CommentCreateRequestDto;
import com.example.mutsaSNS.dto.comment.response.CommentCreateResponseDto;
import com.example.mutsaSNS.exception.ErrorCode;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.mutsaSNS.exception.ErrorCode.NOT_FOUND_USER;
import static com.example.mutsaSNS.exception.ErrorCode.NOT_FOUNT_POST;

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
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUNT_POST, NOT_FOUNT_POST.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        Comment comment = commentRepository.save(createDto.toEntity(user, post));

        return new CommentCreateResponseDto(comment);
    }
}
