package com.example.mutsaSNS.dto.post.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.dto.comment.response.CommentListResponseDto;
import com.example.mutsaSNS.dto.image.response.ImageListResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostOneResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ImageListResponseDto> imageUrl;
    private List<CommentListResponseDto> comments;


    public PostOneResponseDto(final Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.imageUrl = post.getPostImages().stream()
                .map(ImageListResponseDto::new)
                .collect(Collectors.toList());
        this.comments = post.getComments().stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }
}
