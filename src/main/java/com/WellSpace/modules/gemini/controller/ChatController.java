package com.WellSpace.modules.gemini.controller;

import com.WellSpace.modules.gemini.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiService geminiService;

    public ChatController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> perguntar(@RequestBody Map<String, String> body) {
        String pergunta = body.get("text");
        String resposta = geminiService.responder(pergunta);
        return ResponseEntity.ok(Map.of("reply", resposta));
    }
}
