package com.eletra.service;

import com.eletra.dto.LineDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class LineService {
    public List<LineDTO> getAllLines() {
        try{
            RestTemplate restTemplate = new RestTemplate();

            URL url = new URL("http://localhost:3001/api/lines/");

            ResponseEntity<LineDTO[]> response = restTemplate.exchange(url.toURI(), HttpMethod.GET, HttpEntity.EMPTY, LineDTO[].class);

            LineDTO[] lines = response.getBody();

            return Arrays.asList(lines);
        } catch (Exception err) {
            err.printStackTrace(System.err);
        }

        return java.util.Collections.emptyList();
    }
}
