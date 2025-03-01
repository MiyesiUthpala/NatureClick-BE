package com.example.NatureClick.Repository;

import com.example.NatureClick.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {
}
