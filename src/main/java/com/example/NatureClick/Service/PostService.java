package com.example.NatureClick.Service;

import com.example.NatureClick.Entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post); // Upload a new post

    List<Post> getAllPosts(); // View all posts

    Optional<Post> getPostById(Long id); // View a single post by ID

    void deletePost(Long id); // Delete a post
}
