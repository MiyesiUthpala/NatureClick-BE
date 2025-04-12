package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Repository.LikedPostsRepository;
import com.example.NatureClick.Service.LikedPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikedPostsServiceImpl implements LikedPostsService {

    @Autowired
    LikedPostsRepository likedPostsRepository;

    @Override
    public LikedPosts likePost(LikedPosts likedPosts) {

        return likedPostsRepository.save(likedPosts);
    }
}
