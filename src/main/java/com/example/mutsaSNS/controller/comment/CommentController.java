package com.example.mutsaSNS.controller.comment;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.comment.request.CommentCreateRequestDto;
import com.example.mutsaSNS.dto.comment.response.CommentCreateResponseDto;
import com.example.mutsaSNS.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public Response<CommentCreateResponseDto> createComment(@PathVariable final Long postId,
                                                            @RequestBody final CommentCreateRequestDto createDto,
                                                            final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(commentService.createComment(createDto, postId, userId));
    }
}
