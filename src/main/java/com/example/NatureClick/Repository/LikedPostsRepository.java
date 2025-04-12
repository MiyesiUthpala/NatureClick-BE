package com.example.NatureClick.Repository;

import com.example.NatureClick.Entity.LikedPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedPostsRepository extends JpaRepository<LikedPosts, Long> {

    List<LikedPosts> findByUserId(int userId);

    long countByPostId(int postId);

    @Query("SELECT COUNT(l) > 0 FROM LikedPosts l WHERE l.userId = :userId AND l.postId = :postId")
    boolean isPostLikedByUser(@Param("userId") int userId, @Param("postId") long postId);

}
