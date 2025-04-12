package com.example.NatureClick.Entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id" , length = 45)
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int userid;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(name = "user_name", length = 255)
    private String username;

    @Column(name = "email" , length = 255)
    private String email;

    @Column (name = "password", length = 255)
    private String password;

    @Column (name = "mobile", length = 12)
    private String mobile;

    @Column (name = "phone", length = 12)
    private String phone;

    @Column (name = "work", length = 100)
    private String work;

    @Column (name = "dob")
    private Date dob;

    @Column (name = "profile_pic", length = 255)
    private String profilePic;

    @Column (name = "address")
    private String address;

    @Column (name = "bio")
    private String bio;


    public User(int userid, String username, String email, String password, String work, String phone) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public User() {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "user{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
