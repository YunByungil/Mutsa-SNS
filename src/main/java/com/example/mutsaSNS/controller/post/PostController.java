package com.example.mutsaSNS.controller.post;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.post.request.PostCreateRequestDto;
import com.example.mutsaSNS.dto.post.response.PostCreateResponseDto;
import com.example.mutsaSNS.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public Response<PostCreateResponseDto> createPost(final PostCreateRequestDto createDto,
                                                      final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.createPost(createDto, userId));
    }

}
