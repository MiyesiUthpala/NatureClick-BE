package com.example.NatureClick.Repository;


import com.example.NatureClick.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);
    @Modifying
    @Query("UPDATE User u SET u.fullName = :#{#user.fullName}, u.email = :#{#user.email}, u.phone = :#{#user.phone}, u.address = :#{#user.address}, u.bio = :#{#user.bio}, u.profilePic = :#{#user.profilePic} WHERE u.userid = :#{#user.userid}")
    void updateUser(@Param("user") User user);

    User findByEmail(String email);

//    @Query("Select u from User u where u.userid in (SELECT DISTINCT userid FROM Post p WHERE p.id IN :postIds)")
    @Query("SELECT DISTINCT u FROM User u JOIN Post p ON u.userid = p.user.userid WHERE p.id IN :postIds")
    List<User> findUserByPostId(@Param("postIds") List<Integer> postIds);

    @Query(value = "SELECT * FROM user ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<User> findRandomUsers(@Param("userId") int userId );

}
