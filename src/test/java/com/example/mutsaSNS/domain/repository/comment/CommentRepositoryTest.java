package com.example.mutsaSNS.domain.repository.comment;

import com.example.mutsaSNS.domain.entity.comment.Comment;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.post.PostImage;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.post.PostRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @DisplayName("CommentRepository 댓글 생성 기능 테스트")
    @Test
    void saveComment() {
        // given
        User user = createUser("아이디");
        Post post = createPost("제목", "내용111", user, true);
        Comment comment = createComment(user, post, "내용111");

        // when
        Comment findComment = commentRepository.findById(comment.getId()).get();

        // then
        assertThat(findComment.getUser().getUsername()).isEqualTo("아이디");
        assertThat(findComment.getPost().getContent()).isEqualTo("내용111");

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

    private Comment createComment(final User user, final Post post, String content) {
        return commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build());
    }
}