package com.example.mutsaSNS.domain.entity.post;

import com.example.mutsaSNS.domain.entity.BaseTimeEntity;
import com.example.mutsaSNS.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "update Post SET deleted_at = current_timestamp where post_id = ?")
@Where(clause = "deleted_at is null")
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String content;
    private boolean draft;
    private int likeCount;
    private int commentCount;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    @Builder
    public Post(User user, String title, String content, boolean draft) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.draft = draft;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public void updateDraft(boolean draft) {
        this.draft = draft;
    }
}
