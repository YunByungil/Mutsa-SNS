package com.example.mutsaSNS.service.like;

import com.example.mutsaSNS.domain.entity.like.PostLike;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.like.PostLikeRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.like.response.PostLikeCreateResponseDto;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostLikeService {

    private final PostLikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeCreateResponseDto createPostLike(final Long postId, final Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_POST, NOT_FOUND_POST.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        if (post.getUser().getId() == userId) {
            throw new MutsaSnsAppException(NOT_LIKE_MY_POST, NOT_LIKE_MY_POST.getMessage());
        }

        Optional<PostLike> findPostLike = likeRepository.findByPostIdAndUserId(postId, userId);

        if (findPostLike.isPresent()) {
            likeRepository.delete(findPostLike.get());
            post.minusLikeCount();
            return new PostLikeCreateResponseDto(post);
        }

        likeRepository.save(PostLike.builder()
                .post(post)
                .user(user)
                .build());
        post.plusLikeCount();
        return new PostLikeCreateResponseDto(post);
    }
}
