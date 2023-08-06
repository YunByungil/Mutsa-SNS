package com.example.mutsaSNS.domain.repository.post;

import com.example.mutsaSNS.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p " +
            "from Post p " +
            "join fetch p.user " +
            "join fetch p.postImages i " +
            "where p.user.id =:userId " +
            "order by i.id desc")
    List<Post> findAllByUserId(@Param("userId") Long userId);
//    @Query("select p " +
//            "from Post p " +
//            "join fetch p.user " +
//            "left join p.postImages i " +
//            "where p.user.id =:userId")
//    List<Post> findAllByUserId(@Param("userId") Long userId);
}
