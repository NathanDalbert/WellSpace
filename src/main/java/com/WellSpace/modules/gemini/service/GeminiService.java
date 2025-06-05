package com.WellSpace.modules.gemini.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String faqContext = """
        Você é um assistente de suporte de um app de reservas de salas.
        Use apenas as informações abaixo para responder:

        - "Como faço uma reserva de sala?" → "Acesse a aba de busca, selecione a sala, data, horário e confirme."
        - "Posso cancelar uma reserva?" → "Sim, até 24h antes. Vá até 'Reservas' e clique em 'Cancelar'."
        - "Quais formas de pagamento são aceitas?" → "Cartão de crédito, débito e Pix pelo app."
        - "O que fazer se a sala estiver trancada?" → "Contate o suporte via chat informando o problema."

        Se a pergunta não estiver no FAQ, diga que o usuário deve procurar um atendente humano.
        """;


    private String getGeminiUrl() {
        return "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=" + apiKey;
    }


    public String responder(String pergunta) {
        Map<String, Object> message = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", faqContext + "\n\nPergunta do usuário: \"" + pergunta + "\"")
                        ))
                )
        );

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(message, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(getGeminiUrl(), entity, Map.class);

            System.out.println("Resposta completa do Gemini: " + response.getBody());

            Map<String, Object> body = response.getBody();
            if (body == null) {
                return "Resposta vazia do Gemini.";
            }
            if (body.containsKey("error")) {
                return "Erro do Gemini: " + body.get("error");
            }

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                return (String) parts.get(0).get("text");
            } else {
                return "Desculpe, não consegui entender sua pergunta.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro ao buscar a resposta da IA: " + e.getMessage();
        }
    }
}
