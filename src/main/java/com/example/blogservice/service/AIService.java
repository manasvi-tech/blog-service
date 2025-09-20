package com.example.blogservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.Optional;
import com.example.blogservice.entity.Blog;
import com.example.blogservice.repository.BlogRepository;

@Service
public class AIService {
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
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
        String prompt = "Summarize the following blog content (no preamble, short, crisp):\n" + blog.getContent();

        WebClient webClient = WebClient.create();
        Map<String, Object> body = Map.of(
            "contents", new Object[]{Map.of("parts", new Object[]{Map.of("text", prompt)})}
        );

        String url = geminiApiUrl + "?key=" + geminiApiKey;

        Mono<Map> response = webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class);

        Map<String, Object> result = response.block();
        if (result != null && result.containsKey("candidates")) {
            var candidates = (java.util.List<Map<String, Object>>) result.get("candidates");
            if (!candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                var parts = (java.util.List<Map<String, Object>>) content.get("parts");
                if (!parts.isEmpty()) {
                    return (String) parts.get(0).get("text");
                }
            }
        }
        return "Failed to generate summary!";
    }
}
