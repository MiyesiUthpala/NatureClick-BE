package com.example.NatureClick.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecommendedPostsResponse {

    @JsonProperty("recommended_posts") // Maps JSON key to Java field
    private List<RecommendedPost> recommendedPosts;

    // Getters and Setters
    public List<RecommendedPost> getRecommendedPosts() {
        return recommendedPosts;
    }

    public void setRecommendedPosts(List<RecommendedPost> recommendedPosts) {
        this.recommendedPosts = recommendedPosts;
    }

}
