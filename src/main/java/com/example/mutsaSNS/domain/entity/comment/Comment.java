package com.example.mutsaSNS.domain.entity.comment;

import com.example.mutsaSNS.domain.entity.BaseTimeEntity;
import com.example.mutsaSNS.domain.entity.post.Post;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.dto.comment.request.CommentUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "update Comment set deleted_at = current_timestamp where comment_id = ?")
@Where(clause = "deleted_at is null")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(final String content, final User user, final Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void updateComment(final CommentUpdateRequestDto updateDto) {
        this.content = updateDto.getContent();
    }
}
