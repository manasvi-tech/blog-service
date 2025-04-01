package com.example.blogservice.controller;

import com.example.blogservice.entity.Blog;
import com.example.blogservice.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog newBlog = blogService.createBlog(blog);
        return ResponseEntity.ok(newBlog);
    }

    @GetMapping
    public ResponseEntity<Page<Blog>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Blog> blogs = blogService.getAllBlogs(PageRequest.of(page, size));
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        Blog updatedBlog = blogService.updateBlog(id, blog);
        return updatedBlog != null ? ResponseEntity.ok(updatedBlog) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
