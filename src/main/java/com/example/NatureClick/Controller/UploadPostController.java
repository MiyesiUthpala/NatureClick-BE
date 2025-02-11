package com.example.NatureClick.Controller;

import com.example.NatureClick.Entity.UploadPost;
import com.example.NatureClick.Repository.UploadPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class UploadPostController {

    @Autowired
    private UploadPostRepository uploadPostRepository;

    @PostMapping(path = "/create")
    public UploadPost createPost(@RequestBody UploadPost uploadPost) {
        uploadPost.setTime(LocalDateTime.now());
        return uploadPostRepository.save(uploadPost);
    }

    @GetMapping(path = "/all")
    public List<UploadPost> getAllPosts() {
        return uploadPostRepository.findAll();
    }
}
