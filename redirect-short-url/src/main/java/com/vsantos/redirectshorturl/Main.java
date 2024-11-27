package com.vsantos.redirectshorturl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final S3Client s3Client = S3Client.builder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String shortCodeUrl = extractShortUrl(input);
        UrlData urlData = fetchUrlData(shortCodeUrl);

        return buildResponse(urlData);
    }

    private String extractShortUrl(Map<String, Object> input) {
        String pathParameters = input.get("rawPath").toString();
        if (pathParameters == null || pathParameters.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: 'rawPath' is required");
        }
        return pathParameters.replace("/", "");
    }

    private UrlData fetchUrlData(String shortCodeUrl) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("url-shortener-s3bucket")
                .key(shortCodeUrl + ".json")
                .build();

        try (InputStream s3InputStream = s3Client.getObject(getObjectRequest)) {
            return mapper.readValue(s3InputStream, UrlData.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching or parsing URL data from S3: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> buildResponse(UrlData urlData) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> headers = new HashMap<>();

        if (urlData.getExpirationDate().before(new Date())) {
            response.put("statusCode", 410);
            response.put("body", "This URL has expired!");

            return response;
        }

        headers.put("Location", urlData.getOriginalUrl());

        response.put("statusCode", 302);
        response.put("headers", headers);
        return response;

    }

}