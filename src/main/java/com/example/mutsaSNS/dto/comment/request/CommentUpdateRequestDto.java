package com.example.mutsaSNS.dto.comment.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentUpdateRequestDto {

    private String content;

    @Builder
    public CommentUpdateRequestDto(final String content) {
        this.content = content;
    }
}
