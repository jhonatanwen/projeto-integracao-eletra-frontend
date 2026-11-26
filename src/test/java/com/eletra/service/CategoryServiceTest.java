package com.eletra.service;

import com.eletra.dto.CategoryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    private static final String API_URL = CategoryService.API_URL;
    private final Integer lineId = 1;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void getCategoriesByLineIdShouldReturnListOfCategories() throws Exception {
        CategoryDTO cronosOld = new CategoryDTO();
        cronosOld.setName("Cronos Old");
        CategoryDTO cronosL = new CategoryDTO();
        cronosL.setName("Cronos L");
        CategoryDTO[] body = {cronosOld, cronosL};

        URI expectedUri = new URI(API_URL + lineId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, CategoryDTO[].class))
                .thenReturn(ResponseEntity.ok(body));

        List<CategoryDTO> result = categoryService.getCategoriesByLineId(lineId);

        assertNotNull(result);

        assertEquals(2, result.size());

        assertEquals("Cronos Old", result.get(0).getName());
        assertEquals("Cronos L", result.get(1).getName());

        verify(restTemplate, times(1)).exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, CategoryDTO[].class);
    }

    @Test
    public void getCategoriesByLineIdWhenBodyIsNullShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL + lineId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, CategoryDTO[].class))
                .thenReturn(ResponseEntity.ok(null));

        List<CategoryDTO> result = categoryService.getCategoriesByLineId(lineId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getCategoriesByLineIdWhenApiThrowsExceptionShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL + lineId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, CategoryDTO[].class))
                .thenThrow(new RestClientException("Connection refused"));

        List<CategoryDTO> result = categoryService.getCategoriesByLineId(lineId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}