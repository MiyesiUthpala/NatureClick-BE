package com.example.NatureClick.Repository;


import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("SELECT lp FROM LikedPosts lp WHERE lp.userId = :userId")
    List<LikedPosts> findLatestLikedPostsByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.id IN :postIds")
    List<Post> findPostsByIds(@Param("postIds") List<Integer> postIds);

    @Query(value = "SELECT * FROM post ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Post> findRandom();


    @Query("SELECT p FROM Post p WHERE p.user.userid = :userId")
    List<Post> findPostsByUserId(@Param("userId") int userId);

    @Query("Select p FROM Post p WHERE p.type = :type")
    List<Post> findPostsByType(@Param("type") String type);

}
