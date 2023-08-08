package com.example.mutsaSNS.dto.post.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostListResponseDto {

    private Long id;
    private String title;
    private String username;
    private String imageUrl;
    private LocalDateTime deletedAt;

    public PostListResponseDto(final Post post, final User user) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = user.getUsername();
        this.imageUrl = post.getPostImages().stream()
                .map(image -> image.getImage())
                .findFirst().get();
        this.deletedAt = post.getDeletedAt();
    }
}
