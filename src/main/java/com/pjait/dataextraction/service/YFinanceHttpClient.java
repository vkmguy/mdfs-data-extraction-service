package com.pjait.dataextraction.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Service
public class YFinanceHttpClient {

    @Value("${finance.api.host}")
    private String API_BASE_URL;

    private final RestTemplate restTemplate;

    public YFinanceHttpClient(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }


    public <T> ResponseEntity<T> getAndMapToList(String endpoint, ParameterizedTypeReference<T> responseType, Map<String, String> customHeaders) {

        URI baseUrl = URI.create(API_BASE_URL + endpoint);
        HttpEntity<?> httpEntity = new HttpEntity<>(defaultHeaders(customHeaders));

        // Create the HTTP request with the GET method
        return restTemplate.exchange(baseUrl, HttpMethod.GET, httpEntity, responseType);
    }

    protected HttpHeaders defaultHeaders(Map<String, String> customHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.ACCEPT, APPLICATION_JSON);
        Objects.requireNonNull(headers);
        customHeaders.forEach(headers::add);
        return headers;
    }


}