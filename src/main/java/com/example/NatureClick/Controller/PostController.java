package com.example.NatureClick.Controller;

import com.example.NatureClick.Entity.Post;
import com.example.NatureClick.Repository.PostRepository;
import com.example.NatureClick.Service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    private static final String UPLOAD_DIR = "uploads/";

    //upload new post
    @PostMapping("/upload")
    public ResponseEntity<Post> uploadPost(@RequestParam("file") MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("text") String text) {
        try {
            // Ensure upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            byte[] fileBytes = file.getBytes(); // Get the file bytes
            String imageBase64 = Base64.getEncoder().encodeToString(fileBytes); // Convert to Base64 string

            // Generate a unique filename with timestamp
            String timestamp = String.valueOf(System.currentTimeMillis());
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String newFilename = "post_" + timestamp + extension;

            // Save the file with new name
            Path filePath = uploadPath.resolve(newFilename);
            Files.write(filePath, file.getBytes());

            // Create and save post entity
            Post post = new Post();
            post.setTitle(name);
            post.setDescription(text);
            post.setImageBase64(imageBase64);
            post.setImageUrl("/" + UPLOAD_DIR + newFilename); // Store relative URL
            post.setTime(LocalDateTime.now());

            Post savedPost = postService.savePost(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // View all posts
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok( postService.getAllPosts() );
    }

    // View a single post
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
