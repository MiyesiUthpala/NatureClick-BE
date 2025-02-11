package com.example.NatureClick.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class UploadPost {
    @Id
    @Column (name = "id" , length = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "text" , length = 255)
    private String text;

    @Column(name = "imageUrl" , length = 255)
    private String imageUrl;
    @Column(name = "time")
    private LocalDateTime time;

    public UploadPost() {
    }

    public UploadPost(String name, String text, String imageUrl, LocalDateTime time) {
        this.name = name;
        this.text = text;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
