package com.eletra.service;

import com.eletra.dto.ModelDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ModelService {
    static final String API_URL = "http://localhost:3001/api/models/categories/";

    RestTemplate restTemplate =  new RestTemplate();

    public List<ModelDTO> getModelsByCategoryId(int categoryId) {
        try {
            URL url = new URL(API_URL + categoryId);

            ResponseEntity<ModelDTO[]> response =  restTemplate.exchange(url.toURI(), HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class);

            ModelDTO[] models = response.getBody();

            return Arrays.asList(models);
        } catch (Exception err) {
            err.printStackTrace(System.err);
        }

        return java.util.Collections.emptyList();
    }
}
