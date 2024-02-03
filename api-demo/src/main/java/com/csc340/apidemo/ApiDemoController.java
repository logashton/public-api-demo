package com.csc340.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiDemoController {

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Hello World API endpoint.
     *
     * @return response string.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World";
    }

    /**
     * definition API endpoint
     *
     * @param word, the request parameter
     * @return response data
     */
    @GetMapping("/trivia")
    public String getTriviaQuestion(
            @RequestParam(value = "difficulty", defaultValue = "easy") String difficulty) {
        try {
            String url = "https://opentdb.com/api.php?amount=1&type=multiple&difficulty=" + difficulty;
            String jsonWordInfo = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonWordInfo);
            System.out.println(root);
            String question = root.get("results").get(0).get("question").asText();
            return question;

        } catch (JsonProcessingException ex) {
            Logger.getLogger(ApiDemoController.class.getName()).log(Level.SEVERE,
                    null, ex);
            return "error in /quote";
        }
    }
}
