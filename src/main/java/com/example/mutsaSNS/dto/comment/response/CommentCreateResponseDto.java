package com.example.mutsaSNS.dto.comment.response;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentCreateResponseDto {

    private Long id;
    private String username;
    private String content;

    public CommentCreateResponseDto(final Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }
}
