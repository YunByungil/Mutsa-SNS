package com.example.mutsaSNS.controller.like;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.like.response.PostLikeCreateResponseDto;
import com.example.mutsaSNS.service.like.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService likeService;

    @PostMapping("/post/{postId}/like")
    public Response<PostLikeCreateResponseDto> createPostLike(@PathVariable final Long postId,
                                                             final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(likeService.createPostLike(postId, userId));
    }
}
