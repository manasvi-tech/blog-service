package com.example.blogservice.controller;

import com.example.blogservice.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/summarize/{blogId}")
    public ResponseEntity<String> summarizeBlog(@PathVariable Long blogId) {
        String summary = aiService.summarizeBlog(blogId);
        return ResponseEntity.ok(summary);
    }
}
