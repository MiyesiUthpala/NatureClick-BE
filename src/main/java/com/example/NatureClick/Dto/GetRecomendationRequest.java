package com.example.NatureClick.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetRecomendationRequest {

    @JsonProperty("post_ids")
    private List<Integer> postIds;

    public List<Integer> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<Integer> postIds) {
        this.postIds = postIds;
    }
}
