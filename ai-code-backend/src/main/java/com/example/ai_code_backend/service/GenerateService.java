package com.example.ai_code_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class GenerateService {
    @Value("${api.endpoint}")
    private String endpoint;

    @Value("${api.key}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public String callHuggingFace(String context, String prompt) throws Exception {
        String reqJson = mapper.writeValueAsString(Map.of(
                "messages", List.of(
                        Map.of("role", "system", "content", "You are an expert Spring Boot code generator."),
                        Map.of("role", "user", "content", "Here is some context from other files:\n" + context),
                        Map.of("role", "user", "content", "Now generate code for this request:\n" + prompt)
                ),
                "model", "openai/gpt-oss-20b:nebius",
                "stream", false
        ));

        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(reqJson));

        if (apiKey != null && !apiKey.isEmpty()) {
            b.header("Authorization", "Bearer " + apiKey);
        }

        HttpResponse<String> resp = client.send(b.build(), HttpResponse.BodyHandlers.ofString());

        Map<String, Object> json = mapper.readValue(resp.body(), Map.class);
        var choices = (List<Map<String, Object>>) json.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            if (message != null) {
                return (String) message.get("content");
            }
        }

        return resp.body();
    }
}