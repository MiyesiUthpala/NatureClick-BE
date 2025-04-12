package com.example.NatureClick.Controller;

import com.example.NatureClick.Dto.RecommendedPostsResponse;
import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Entity.Post;
import com.example.NatureClick.Service.LikedPostsService;
import com.example.NatureClick.Service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private PostService postService;
    @Autowired
    private LikedPostsService likedPostsService;

    private static final String UPLOAD_DIR = "uploads/";

    private final RestTemplate restTemplate = new RestTemplate();  // RestTemplate to make HTTP requests

    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        post.setTime(LocalDateTime.now());
        Post newPost = postService.savePost(post);
        return ResponseEntity.ok(newPost);
    }

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
//            String imageBase64 = Base64.getEncoder().encodeToString(fileBytes); // Convert to Base64 string

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
//            post.setImageBase64(imageBase64);
            post.setImageUrl("/" + UPLOAD_DIR + newFilename); // Store relative URL
            post.setTime(null);

            Post savedPost = postService.savePost(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // View all posts
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam int userId, String type) {
        if (type.equalsIgnoreCase("All"))
        {
            return ResponseEntity.ok( postService.getAllPosts(userId) );
        }
        else if (type.equalsIgnoreCase("recommendations")){
            return ResponseEntity.ok( postService.getRecommendedPosts(userId) );
        }
        else {
            return ResponseEntity.ok( postService.getPostsByType(userId, type));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/profile")
    public ResponseEntity<List<Post>> getPostsByUser(@RequestParam int userId) {
        return ResponseEntity.ok( postService.getPostsByUser(userId) );
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

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikedPosts likedPost) {
        try {
            // Like the post first
            likedPostsService.likePost(likedPost);

            // After liking the post, trigger the retrain API
            String retrainUrl = "http://localhost:5000/retrain";  // The URL of your retrain endpoint
            restTemplate.postForObject(retrainUrl, null, String.class);  // Sending a POST request to retrain

            return ResponseEntity.ok("Post liked successfully and model retrained");
        } catch (Exception e) {
            log.error("Error liking post", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recommended/{id}")
    public ResponseEntity<List<Post>> getRecommendedPosts(@PathVariable int id) {
        List<Post> posts = postService.getRecommendedPosts(id);
        return ResponseEntity.ok(posts);
    }
}
