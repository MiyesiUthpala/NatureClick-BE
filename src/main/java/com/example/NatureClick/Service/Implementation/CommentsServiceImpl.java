package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Entity.Comment;
import com.example.NatureClick.Repository.CommentsRepository;
import com.example.NatureClick.Service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(int postId) {
        return commentsRepository.findByPostId(postId);
    }
}
