package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Dto.GetRecomendationRequest;
import com.example.NatureClick.Dto.RecommendedPost;
import com.example.NatureClick.Dto.RecommendedPostsResponse;
import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Entity.Post;
import com.example.NatureClick.Entity.User;
import com.example.NatureClick.Repository.LikedPostsRepository;
import com.example.NatureClick.Repository.PostRepository;
import com.example.NatureClick.Repository.UserRepository;
import com.example.NatureClick.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PostImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedPostsRepository likedPostsRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts( int userId ) {
        Pageable pageable = PageRequest.of(0, 15); // Page 0 with 10 records
        return postRepository.findAll(pageable).getContent().stream().map(post -> {
            post.setLiked(likedPostsRepository.isPostLikedByUser(userId, post.getId()));
            return post;
        }).toList();
    }

    @Override
    public List<Post> getPostsByType( int userId, String type ) {
        Pageable pageable = PageRequest.of(0, 15); // Page 0 with 10 records
        return postRepository.findPostsByType(type).stream().map(post -> {
            post.setLiked(likedPostsRepository.isPostLikedByUser(userId, post.getId()));
            return post;
        }).toList();
    }

    @Override
    public List<Post> getPostsByUser( int userId ) {
        return postRepository.findPostsByUserId(userId).stream().map(post -> {
            post.setLiked(likedPostsRepository.isPostLikedByUser(userId, post.getId()));
            return post;
        }).toList();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getRecommendedPosts(int userId) {

        Pageable pageable = PageRequest.of(0, 10);
        List<LikedPosts> latestLikedPosts = postRepository.findLatestLikedPostsByUserId(userId, pageable);
        ArrayList<Integer> postIds = new ArrayList<>();
        for (LikedPosts likedPost : latestLikedPosts) {
            System.out.println(
                    "Liked post id: " + likedPost.getPostId() + " by user: " + likedPost.getUserId()
            );
            postIds.add(likedPost.getPostId());
        }
        GetRecomendationRequest request = new GetRecomendationRequest();
        request.setPostIds(postIds);

        String url = "http://127.0.0.1:5000/recommend?";
        RecommendedPostsResponse recommendedPosts = webClientBuilder.build()
                .post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RecommendedPostsResponse.class)
                .block();

        if (recommendedPosts == null || recommendedPosts.getRecommendedPosts() == null || recommendedPosts.getRecommendedPosts().isEmpty()) {
            return postRepository.findRandom();
        }

        List<Integer> postIdsList = recommendedPosts.getRecommendedPosts().stream()
            .map(RecommendedPost::getPostId)
            .toList();

        return postRepository.findPostsByIds(postIdsList);
    }

}
