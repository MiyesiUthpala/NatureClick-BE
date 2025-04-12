package com.example.NatureClick.Service;

import com.example.NatureClick.Dto.RecommendedPostsResponse;
import com.example.NatureClick.Entity.Post;
import com.example.NatureClick.Entity.User;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post); // Upload a new post

    List<Post> getPostsByUser( int userId );

    List<Post> getAllPosts( int userId ); // View all posts

    List<Post> getPostsByType( int userId, String type );

    Optional<Post> getPostById(Long id); // View a single post by ID

    void deletePost(Long id); // Delete a post

    List<Post> getRecommendedPosts(int userId); // Get recommended posts

}
