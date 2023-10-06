package com.example.platform.controllers;

import com.example.platform.models.QuestionModel;
import com.example.platform.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/compile")
@RequiredArgsConstructor
public class PythonCodeController {

    @Autowired
    private final QuestionRepository questionRepository;

    @GetMapping("/executePython/{code}/{questionId}")
    public ResponseEntity<Boolean> executePython(@PathVariable("code") String base64Code, @PathVariable("questionId") UUID qid) {
        QuestionModel questionModel = questionRepository.getReferenceById(qid);

        // testcase should be in this form "[4, 2, 3], 3"
        String testcases = questionModel.getParameters();
        String expectedOutput = questionModel.getExpectedOutput();

        String decodedPythonCode = decodeBase64(base64Code);

        decodedPythonCode += "\nprint(execute(" + testcases + "))";

        // Save the Python code to a file
        String fileName = "dynamic_code" + System.currentTimeMillis() + ".py";
        saveToFile(decodedPythonCode, fileName);

        // Execute the Python code and capture the output
        String executionOutput = executePythonCode(fileName).trim();
        if(executionOutput.equals(expectedOutput)) {

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    private String decodeBase64(String base64Code) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Code);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private void saveToFile(String code, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(code);
            writer.close();
        } catch (IOException e) {
            // Handle file I/O errors
            System.out.println("error creating the file:" + e.getMessage());
        }
    }

    private String executePythonCode(String fileName) {
        // Execute the Python code using an external Python interpreter
        // You can use ProcessBuilder to run the Python script.
        try {
            Process process = new ProcessBuilder("python3", fileName).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString();
        } catch (IOException | InterruptedException e) {
            // Handle execution errors
            return "Error executing Python code: " + e.getMessage();
        }
    }
}
