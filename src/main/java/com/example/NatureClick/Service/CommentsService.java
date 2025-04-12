package com.example.NatureClick.Service;

import com.example.NatureClick.Entity.Comment;

import java.util.List;

public interface CommentsService {

    Comment addComment(Comment comment);

    List<Comment> getComments(int postId);

}
