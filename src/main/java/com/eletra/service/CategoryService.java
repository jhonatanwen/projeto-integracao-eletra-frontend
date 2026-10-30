package com.eletra.service;

import com.eletra.dto.CategoryDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class CategoryService {
    public List<CategoryDTO>  getCategoriesByLineId(Integer lineId) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URL url = new URL("http://localhost:3001/api/categories/lines/" + lineId);

            ResponseEntity<CategoryDTO[]> response =  restTemplate.exchange(url.toURI(), HttpMethod.GET, HttpEntity.EMPTY, CategoryDTO[].class);

            CategoryDTO[] categories = response.getBody();

            return Arrays.asList(categories);
        } catch (Exception err) {
            err.printStackTrace(System.err);
        }

        return java.util.Collections.emptyList();
    }
}
