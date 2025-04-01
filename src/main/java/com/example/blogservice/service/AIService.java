package com.example.blogservice.service;

import com.example.blogservice.entity.Blog;
import com.example.blogservice.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.api.url}")
    private String openaiApiUrl;

    @Value("${openai.model}")
    private String openaiModel;

    private final BlogRepository blogRepository;

    public AIService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public String summarizeBlog(Long blogId) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);

        if (blogOpt.isEmpty()) {
            return "Blog not found!";
        }

        Blog blog = blogOpt.get();
        String content = blog.content;

        String prompt = "Summarize the following blog content:\n" + content;

        WebClient webClient = WebClient.create(openaiApiUrl);

        Map<String, Object> requestBody = Map.of(
                "model", openaiModel,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 500,
                "temperature", 0.7
        );

        Mono<Map> response = webClient.post()
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, Object> result = response.block();

        if (result != null && result.containsKey("choices")) {
            // Fix: Cast to List<Map<String, Object>> instead of Array
            List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");

            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        }

        return "Failed to generate summary!";
    }
}
