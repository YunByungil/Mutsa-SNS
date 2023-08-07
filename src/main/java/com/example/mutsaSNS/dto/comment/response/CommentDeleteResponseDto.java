package com.example.mutsaSNS.dto.comment.response;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentDeleteResponseDto {

    private Long id;
    private String content;

    public CommentDeleteResponseDto(final Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
