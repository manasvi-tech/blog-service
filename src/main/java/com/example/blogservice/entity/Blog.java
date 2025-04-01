package com.example.blogservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    @Column(columnDefinition = "TEXT")
    public String content;

    public String author;

    public LocalDateTime createdAt = LocalDateTime.now();

//    public void setId(Long id) {
//    }

//    public String getContent() {
//    }
}

