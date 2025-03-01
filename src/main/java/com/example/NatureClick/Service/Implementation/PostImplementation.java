package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Entity.Post;
import com.example.NatureClick.Repository.PostRepository;
import com.example.NatureClick.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
