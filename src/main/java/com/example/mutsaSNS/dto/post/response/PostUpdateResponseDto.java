package com.example.mutsaSNS.dto.post.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostUpdateResponseDto {

    private Long id;
    private List<String> images;

    public PostUpdateResponseDto(final Post post, final List<String> images) {
        this.id = post.getId();
        this.images = images;
    }
}
