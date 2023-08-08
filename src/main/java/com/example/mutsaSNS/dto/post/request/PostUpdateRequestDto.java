package com.example.mutsaSNS.dto.post.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostUpdateRequestDto {

    private List<MultipartFile> images;

    @Builder
    public PostUpdateRequestDto(final List<MultipartFile> images) {
        this.images = images;
    }
}
