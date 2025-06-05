package com.cloud.computing.container1.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.cloud.computing.container1.model.FailureResponse;
import com.cloud.computing.container1.model.FileRequest;
import com.cloud.computing.container1.model.FileSuccessResponse;
import com.cloud.computing.container1.model.RequestParameters;
import com.cloud.computing.container1.model.SuccessResponse;
import com.cloud.computing.container1.model.SuccessResponseSum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class CheckFile {

    protected Logger log = LoggerFactory.getLogger(CheckFile.class);
    SuccessResponse successResponse;
    FailureResponse errorResponse;
    ResponseEntity<Object> responseEntity;
    SuccessResponseSum successResponseSum;

    private static final String STORAGE_DIRECTORY = "/home/Unnati_PV_dir";

    @PostMapping("/store-file")
    public ResponseEntity<?> storeFile(@RequestBody FileRequest fileRequest) {
        String fileName = fileRequest.getFile();
        String data = fileRequest.getData();

        // Check if file name is provided
        if (fileName == null || fileName.isEmpty() ){
            return new ResponseEntity<>(new FailureResponse(null, "Invalid JSON input."), HttpStatus.BAD_REQUEST);
        }

        // Store the file
        try {
            File file = new File(STORAGE_DIRECTORY, fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
            return new ResponseEntity<>(new FileSuccessResponse(fileName, "Success."), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FailureResponse(fileName, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(path = "/calculate")
    public ResponseEntity<Object> checkFileExists(@RequestBody RequestParameters request)
            throws JsonMappingException, JsonProcessingException {
        if (request.getFile() == null || request.getFile().isEmpty()) {
            errorResponse = new FailureResponse();
            errorResponse.setFile(request.getFile());
            errorResponse.setError("Invalid JSON input.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {

            //if file exists then ths code will run
            File tempFile = new File(STORAGE_DIRECTORY, request.getFile());
            boolean exists = tempFile.exists();
            if (exists) {
                try {

                    //Making an api call 
                    responseEntity = makeApiCall(request);
                    System.out.println("Unnati"+ responseEntity+"");
                } catch (HttpServerErrorException.InternalServerError internalServerError) {
                    errorResponse = new FailureResponse();
                    errorResponse.setFile(request.getFile());
                    errorResponse.setError("Input file not in CSV format.");
                    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
                } 

            } else {
                errorResponse = new FailureResponse();
                errorResponse.setFile(request.getFile());
                errorResponse.setError("File not found.");
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

        }
        return responseEntity;

    }

    protected ResponseEntity<Object> makeApiCall(@RequestBody RequestParameters request)
            throws JsonProcessingException {
        final String apiUrl = "http://localhost:6002/sum";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        java.util.Map<String, String> requestBodyMap = new java.util.HashMap<>();
        requestBodyMap.put("file", request.getFile());
        requestBodyMap.put("product", request.getProduct());
        String requestBody;
        requestBody = objectMapper.writeValueAsString(requestBodyMap);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
                Object.class);
        return responseEntity;
    }

}
