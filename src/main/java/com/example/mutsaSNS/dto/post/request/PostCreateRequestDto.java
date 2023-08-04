package com.example.mutsaSNS.dto.post.request;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCreateRequestDto {

    private String title;
    private String content;
    private List<MultipartFile> images;

    @Builder
    public PostCreateRequestDto(final String title, final String content, final List<MultipartFile> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public Post toEntity(User user, boolean draft) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .draft(draft)
                .build();
    }
}
