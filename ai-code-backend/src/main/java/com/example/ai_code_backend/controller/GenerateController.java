package com.example.ai_code_backend.controller;

import com.example.ai_code_backend.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GenerateController {

    @Autowired
    private GenerateService generateService;

    @PostMapping("/generate")
    public Map<String, String> generate(@RequestBody Map<String, Object> body) throws Exception {
        String prompt = (String) body.get("prompt");

        List<Map<String, String>> contextFiles = (List<Map<String, String>>) body.get("contextFiles");

        StringBuilder contextBuilder = new StringBuilder();
        if (contextFiles != null) {
            for (Map<String, String> file : contextFiles) {
                contextBuilder.append("File: ").append(file.get("name")).append("\n");
                contextBuilder.append(file.get("content")).append("\n\n");
            }
        }

        // Send context and prompt separately
        String code = generateService.callHuggingFace(contextBuilder.toString(), prompt);
        return Map.of("generatedCode", code);
    }
}