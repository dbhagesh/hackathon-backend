package com.example.platform.controllers;

import com.example.platform.models.GenAIResponse;
import com.example.platform.models.QuestionModel;
import com.example.platform.repository.QuestionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/gen-ai")
public class GenAIController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Gson gson;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/optimize")
    public ResponseEntity<String> giveOptimizedAnswer(){

            RestTemplate restTemplate = new RestTemplate();

            // Define the API URL
            String apiUrl = "http://services.test2.ff-services-test2.cluster.infoedge.com/prompt-execute-services/vertex/vertex-api/text/completions";

            // Set request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AppId", "1");
            headers.set("SystemId", "1");
            headers.set("templateCode", "HACKATHON_1");
            headers.set("key", "bb76e82673352d53e4e3cab1b9720c43f6bc1cc0def322dbbf0d8d89c45aab99e1518b7e20e5892c");
            headers.set("Cookie", "_t_ds=271160863224221-1695813206-1695813206-1695813206; _t_ds=365658480084201-1696480485-1696480485-1696480485");

            // Create the request body
            String requestBody = "{\"prompt\":\"hello, how are you ?\",\"parameters\":{\"temperature\":1,\"maxOutputTokens\":250,\"topK\":1,\"topP\":0.5},\"model\":\"text-bison\"}";

            // Create the HTTP entity with headers and body
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the POST request
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // Check the response status code
            HttpStatus statusCode = responseEntity.getStatusCode();


        if (statusCode == HttpStatus.OK) {
            // Request was successful, you can access the response body
            String responseBody = responseEntity.getBody();
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            // Handle the error or non-OK status code
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }


    @GetMapping("/qualityFeedback")
    public ResponseEntity<String> giveQualityFeedback(){

        RestTemplate restTemplate = new RestTemplate();

        // Define the API URL
        String apiUrl = "http://services.test2.ff-services-test2.cluster.infoedge.com/prompt-execute-services/vertex/vertex-api/text/completions";

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AppId", "1");
        headers.set("SystemId", "1");
        headers.set("templateCode", "HACKATHON_1");
        headers.set("key", "bb76e82673352d53e4e3cab1b9720c43f6bc1cc0def322dbbf0d8d89c45aab99e1518b7e20e5892c");
        headers.set("Cookie", "_t_ds=271160863224221-1695813206-1695813206-1695813206; _t_ds=365658480084201-1696480485-1696480485-1696480485");

        // Create the request body
        String requestBody = "{\"prompt\":\"hello, how are you ?\",\"parameters\":{\"temperature\":1,\"maxOutputTokens\":250,\"topK\":1,\"topP\":0.5},\"model\":\"text-bison\"}";

        // Create the HTTP entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Check the response status code
        HttpStatus statusCode = responseEntity.getStatusCode();


        if (statusCode == HttpStatus.OK) {
            // Request was successful, you can access the response body
            String responseBody = responseEntity.getBody();
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            // Handle the error or non-OK status code
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/generateWithMagic")
    public ResponseEntity<QuestionModel> generateQuestionsWithMagic() {

        RestTemplate restTemplate = new RestTemplate();

        // Define the API URL
        String apiUrl = "http://services.test2.ff-services-test2.cluster.infoedge.com/prompt-execute-services/vertex/vertex-api/text/completions";

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AppId", "1");
        headers.set("SystemId", "1");
        headers.set("templateCode", "HACKATHON_1");
        headers.set("key", "bb76e82673352d53e4e3cab1b9720c43f6bc1cc0def322dbbf0d8d89c45aab99e1518b7e20e5892c");
        headers.set("Cookie", "_t_ds=271160863224221-1695813206-1695813206-1695813206; _t_ds=365658480084201-1696480485-1696480485-1696480485");

        // Create the request body
        String requestBody = "{\"prompt\":\"Please create one random python coding question, with a title and description and level of that coding question. Output should be JSON.Examples: title:Add two number , description:write a function to add two integer numbers, level: Easy, parameters: '1, 2' do not nest parameters in list give it as a string as shown in example., expectedOutput: 3, functionTemplate: def execute(a,b): #write your code here\",\"parameters\":{\"temperature\":1,\"maxOutputTokens\":250,\"topK\":1,\"topP\":0.5},\"model\":\"text-bison\"}";

        // Create the HTTP entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Check the response status code
        HttpStatus statusCode = responseEntity.getStatusCode();


        if (statusCode == HttpStatus.OK) {
            // Request was successful, you can access the response body
            String responseBody = responseEntity.getBody();

            // Use Jackson ObjectMapper to deserialize the JSON response into your Java object
            ObjectMapper objectMapper = new ObjectMapper();
            GenAIResponse myResponse;

            try {
                myResponse = objectMapper.readValue(responseBody, GenAIResponse.class);
                String rawJson = myResponse.getData().getResponse();

                int startIndex = rawJson.indexOf("{");
                int endIndex = rawJson.lastIndexOf("}");

                String cleanedJson = rawJson.substring(startIndex, endIndex + 1);

                QuestionModel questionModel = gson.fromJson(cleanedJson,QuestionModel.class);

                QuestionModel saveModel = questionRepository.save(questionModel);
                System.out.println("Deserialized Response: " + myResponse);


                return new ResponseEntity<>(saveModel, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        } else {
            // Handle the error or non-OK status code
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



