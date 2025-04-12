package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Dto.*;
import com.example.NatureClick.Entity.LikedPosts;
import com.example.NatureClick.Entity.User;
import com.example.NatureClick.Repository.PostRepository;
import com.example.NatureClick.Repository.UserRepository;
import com.example.NatureClick.Service.UserService;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public String addUser(UserDTO userDTO) {

        User user = new User();
        user.setUserid(userDTO.getUserid());
        user.setPhone(userDTO.getPhone());
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.isUpdate() ? userDTO.getPassword() : this.passwordEncoder.encode(userDTO.getPassword()) );
        user.setWork(userDTO.getWork());
        user.setPhone(userDTO.getPhone());

        userRepository.save(user);

        return user.getUsername();
    }
    @Transactional
    @Override
    public void updateUser(UserDTO userDTO) {

        User user = new User();
        user.setUserid(userDTO.getUserid());
        user.setPhone(userDTO.getPhone());
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setWork(userDTO.getWork());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setBio(userDTO.getBio());
        user.setProfilePic(userDTO.getProfilePic());

        userRepository.updateUser(user);
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        User user1 = userRepository.findByEmail(loginDTO.getEmail());
        if (user1 != null){
                String password = loginDTO.getPassword();
                String encodedPassword = user1.getPassword();
                boolean isPwRight = passwordEncoder.matches(password, encodedPassword);
                if (isPwRight){
                    Optional <User> user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                    if(user.isPresent()){
                        return  new LoginResponse("Login Success",true, user.get().getUserid());
                    } else {
                        return new LoginResponse("Login Failed", false, 0);
                    }
                } else {
                    return new LoginResponse("Password Not Match", false, 0);
                }
        } else {
            return new LoginResponse("Email Not exits", false, 0);
        }
    }

    @Override
    public List<User> getRecommendedUsers(int userId) {

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

        if (recommendedPosts == null || recommendedPosts.getRecommendedPosts() == null || recommendedPosts.getRecommendedPosts().isEmpty() ) {
            return userRepository.findRandomUsers( userId );
        }

        List<Integer> postIdsList = recommendedPosts.getRecommendedPosts().stream()
                .map(RecommendedPost::getPostId)
                .toList();

        System.out.println("Recommended post ids: " + postIdsList);

        return userRepository.findUserByPostId(postIdsList);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(new User());
    }
}
