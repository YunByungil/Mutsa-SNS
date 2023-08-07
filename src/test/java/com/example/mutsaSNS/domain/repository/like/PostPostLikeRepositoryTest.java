package com.example.mutsaSNS.domain.repository.like;

import com.example.mutsaSNS.domain.entity.like.PostLike;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PostPostLikeRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @DisplayName("LikeRepository#save() 메서드 테스트")
    @Test
    void saveLike() {
        // given
        User user = createUser("아이디");
        Post post = createPost("제목", "내용", user, true);

        PostLike savedPostLike = postLikeRepository.save(PostLike.builder()
                .post(post)
                .user(user)
                .build());

        // when
        PostLike postLike = postLikeRepository.findById(savedPostLike.getId()).get();

        // then
        assertThat(postLike.getUser().getUsername()).isEqualTo("아이디");
        assertThat(postLike.getPost().getTitle()).isEqualTo("제목");

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
}