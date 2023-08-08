package com.example.mutsaSNS.dto.post.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostDeleteResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime deletedAt;

    public PostDeleteResponseDto(final Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deletedAt = post.getDeletedAt();
    }
}
