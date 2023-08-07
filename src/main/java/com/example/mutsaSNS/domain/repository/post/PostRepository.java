package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p " +
            "from Post p " +
            "join fetch p.user " +
            "join fetch p.postImages i " +
            "where p.user.id =:userId " +
            "order by p.id desc " +
            ", i.id asc")
    List<Post> findAllByUserId(@Param("userId") Long userId);

    @Query("select p " +
            "from Post p " +
            "join fetch p.user " +
            "join fetch p.postImages i " +
            "where p.id =:postId")
    Optional<Post> findAllByPostId(@Param("postId") Long postId);

    @Query("select p " +
            "from Post p " +
            "join fetch p.user " +
            "join fetch p.postImages i " +
            "where p.user.id in :followingId " +
            "order by p.id desc")
    List<Post> customFindAllByFollowingId(@Param("followingId") List<Long> followingId);
}
