package com.example.mutsaSNS.dto.comment.request;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentCreateRequestDto {

    private String content;

    @Builder
    public CommentCreateRequestDto(final String content) {
        this.content = content;
    }

    public Comment toEntity(final User user, final Post post) {
        return Comment.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();
    }
}
