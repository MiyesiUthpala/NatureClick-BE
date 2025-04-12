package com.example.NatureClick.Controller;

import com.example.NatureClick.Entity.Comment;
import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @PostMapping("/add")
    public ResponseEntity<Comment> likePost(@RequestBody Comment comment) {
        try {
            return ResponseEntity.ok(commentsService.addComment(comment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@RequestParam int postId)
    {
        return ResponseEntity.ok(commentsService.getComments(postId));
    }


}
