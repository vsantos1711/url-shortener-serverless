package com.vsantos.urlshortener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final S3Client s3Client = S3Client.builder().build();

    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {

        UrlData urlData = getUrlData(input);
        String shortUrlCode = UUID.randomUUID().toString().substring(0, 8);

        return saveUrlDataToS3(urlData, shortUrlCode);
    }

    private UrlData getUrlData(Map<String, Object> input) {
        String body = input.get("body").toString();
        try {
            return mapper.readValue(body, UrlData.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing request body: " + e.getMessage(), e);
        }
    }

    private Map<String, String> saveUrlDataToS3(UrlData urlData, String shortUrlCode) {
        try {
            String urlDataJson = mapper.writeValueAsString(urlData);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("url-shortener-s3bucket")
                    .key(shortUrlCode + ".json")
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromString(urlDataJson));
        } catch (Exception e) {
            throw new RuntimeException("Error saving data to S3: " + e.getMessage(), e);
        }

        Map<String, String> response = new HashMap<>();
        response.put("code", shortUrlCode);

        return response;
    }
}