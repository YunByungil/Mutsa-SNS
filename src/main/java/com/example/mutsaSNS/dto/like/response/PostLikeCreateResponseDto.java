package com.example.mutsaSNS.dto.like.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostLikeCreateResponseDto {

    private Long postId;
    private int likeCount;

    public PostLikeCreateResponseDto(final Post post) {
        this.postId = post.getId();
        this.likeCount = post.getLikeCount();
    }
}
