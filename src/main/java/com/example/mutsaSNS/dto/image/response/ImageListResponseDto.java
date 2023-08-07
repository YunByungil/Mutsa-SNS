package com.example.mutsaSNS.dto.image.response;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageListResponseDto {

    private Long id;
    private String imageUrl;

    public ImageListResponseDto(final PostImage postImage) {
        this.id = postImage.getId();
        this.imageUrl = postImage.getImage();
    }
}
