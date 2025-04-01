package com.example.blogservice.service;

import com.example.blogservice.entity.Blog;
import com.example.blogservice.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(Blog blog) {
        System.out.println(blog.author);
        return blogRepository.save(blog);
    }

    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog updateBlog(Long id, Blog blog) {
        if (blogRepository.existsById(id)) {
            Optional<Blog> oldBlog = blogRepository.findById(id);
            return blogRepository.save(oldBlog.orElseThrow());
        }
        return null;
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
