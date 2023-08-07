package com.example.mutsaSNS.controller.comment;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.comment.request.CommentCreateRequestDto;
import com.example.mutsaSNS.dto.comment.request.CommentUpdateRequestDto;
import com.example.mutsaSNS.dto.comment.response.CommentCreateResponseDto;
import com.example.mutsaSNS.dto.comment.response.CommentDeleteResponseDto;
import com.example.mutsaSNS.dto.comment.response.CommentUpdateResponseDto;
import com.example.mutsaSNS.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/comment/{commentId}")
    public Response<CommentUpdateResponseDto> updateComment(@PathVariable final Long commentId,
                                                            @RequestBody final CommentUpdateRequestDto updateDto,
                                                            final Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(commentService.updateComment(updateDto, commentId, userId));
    }

    @DeleteMapping("/comment/{commentId}")
    public Response<CommentDeleteResponseDto> deleteComment(@PathVariable final Long commentId,
                                                            final Authentication authentication) {
        System.out.println("commentId = " + commentId);
        Long userId = Long.parseLong(authentication.getName());
        return Response.success(commentService.deleteComment(commentId, userId));
    }
}
