package com.example.mutsaSNS.dto.post.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCreateResponseDto {

    private Long id;
    private String title;
    private String content;

    public PostCreateResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
