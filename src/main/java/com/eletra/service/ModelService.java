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
    public List<ModelDTO> getModelsByCategoryId(int categoryId) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URL url = new URL("http://localhost:3001/api/models/categories/" + categoryId);

            ResponseEntity<ModelDTO[]> response =  restTemplate.exchange(url.toURI(), HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class);

            ModelDTO[] models = response.getBody();

            return Arrays.asList(models);
        } catch (Exception err) {
            err.printStackTrace(System.err);
        }

        return java.util.Collections.emptyList();
    }
}
