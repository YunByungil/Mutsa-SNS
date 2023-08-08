package com.example.mutsaSNS.service.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.post.PostImageRepository;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.post.request.PostCreateRequestDto;
import com.example.mutsaSNS.dto.post.response.PostCreateResponseDto;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @DisplayName("게시글 작성 테스트 (이미지 X)")
    @Test
    void createPostNoImage() {
        // given
        User user = createUser();
        PostCreateRequestDto createDto = PostCreateRequestDto.builder()
                .title("제목")
                .content("내용")
                .build();

        PostCreateResponseDto post = postService.createPost(createDto, user.getId());

        // when
        Post findPost = postRepository.findById(post.getId()).get();

        // then
        assertThat(findPost.isDraft()).isTrue();
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getDeletedAt()).isNull();
    }

    @DisplayName("게시글 작성 테스트 (이미지 O)")
    @Test
    void createPostHaveImage() {
        // given
        User user = createUser();
        List<MultipartFile> image = Collections.singletonList(
                new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[]{}));
        PostCreateRequestDto createDto = PostCreateRequestDto.builder()
                .title("제목")
                .content("내용")
                .images(image)
                .build();

        PostCreateResponseDto post = postService.createPost(createDto, user.getId());

        // when
        Post findPost = postRepository.findById(post.getId()).get();

        // then
        assertThat(findPost.isDraft()).isFalse();
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getDeletedAt()).isNull();
    }


    private User createUser() {
        return userRepository.save(User.builder()
                .username("아이디")
                .password("비밀번호")
                .build());
    }
}