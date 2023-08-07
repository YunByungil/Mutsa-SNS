package com.example.mutsaSNS.controller.post;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.post.request.PostCreateRequestDto;
import com.example.mutsaSNS.dto.post.request.PostUpdateRequestDto;
import com.example.mutsaSNS.dto.post.response.*;
import com.example.mutsaSNS.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{username}")
    public Response<List<PostListResponseDto>> readAllPost(@PathVariable String username) {
        return Response.success(postService.readAllPost(username));
    }

    @GetMapping("/post/{postId}")
    public Response<PostOneResponseDto> readOnePost(@PathVariable final Long postId,
                                                    final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.readOnePost(postId, userId));
    }

    @PutMapping("/post/{postId}")
    public Response<PostUpdateResponseDto> updatePost(@PathVariable final Long postId,
                                                      final PostUpdateRequestDto updateDto,
                                                      final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.updatePost(updateDto, postId, userId));
    }

    @DeleteMapping("/post/{postId}/{imageId}")
    public Response<PostUpdateResponseDto> deleteImages(@PathVariable final Long postId,
                                                        @PathVariable final Long imageId,
                                                        final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.deleteImages(postId, imageId, userId));
    }

    @DeleteMapping("/post/{postId}")
    public Response<PostDeleteResponseDto> deletePost(@PathVariable final Long postId,
                                                      final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.deletePost(postId, userId));
    }

    @GetMapping("/post/following")
    public Response<List<PostListResponseDto>> getFollowingPost(final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(postService.getFollowingPost(userId));
    }
}
