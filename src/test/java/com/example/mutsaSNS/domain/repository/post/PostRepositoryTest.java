package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @DisplayName("유저의 게시글 목록 불러오기#findAllByUserId() 메서드 테스트 (기본 이미지)")
    @Test
    void findAllByUserId() {
        // given
        User user = createUser("username");
        Post post = createPost("title", "content", user, true);
        PostImage image = createPostImage(post, "image");
        post.getPostImages().add(image);

        // when
        List<Post> allByUserId = postRepository.findAllByUserId(user.getId());

        // then
        assertThat(allByUserId.size()).isEqualTo(1);
        assertThat(allByUserId.get(0).isDraft()).isTrue();
        assertThat(allByUserId.get(0).getUser().getUsername()).isEqualTo("username");
        assertThat(allByUserId.get(0).getPostImages().get(0).getImage()).isEqualTo("image");

    }

    private User createUser(final String username) {
        return userRepository.save(User.builder()
                .username(username)
                .password("비밀번호")
                .build());
    }

    private Post createPost(final String title, final String content, final User user, final boolean draft) {
        return postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .draft(draft)
                .build());
    }

    private PostImage createPostImage(final Post post, final String imageUrl) {
        return imageRepository.save(PostImage.builder()
                .post(post)
                .image(imageUrl)
                .build());
    }

}