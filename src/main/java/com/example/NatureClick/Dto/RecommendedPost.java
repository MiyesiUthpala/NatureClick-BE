package com.example.NatureClick.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecommendedPost {

    @JsonProperty("post_id") // Maps JSON key "post_id" to Java field
    private int postId;

    private String title;

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
