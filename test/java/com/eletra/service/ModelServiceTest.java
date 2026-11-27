package com.eletra.service;

import com.eletra.dto.ModelDTO;
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
public class ModelServiceTest {
    private static final String API_URL = ModelService.API_URL;
    private final Integer categoryId = 4;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ModelService modelService;

    @Test
    public void getModelsByCategoryIdShouldReturnListOfModels() throws Exception {
        ModelDTO model7021 = new ModelDTO(12, "ARES 7021", 4);
        ModelDTO model7031 = new ModelDTO(13, "ARES 7031", 4);
        ModelDTO[] body = {model7021, model7031};

        URI expectedUri = new URI(API_URL + categoryId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class))
                .thenReturn(ResponseEntity.ok(body));

        List<ModelDTO> result = modelService.getModelsByCategoryId(categoryId);

        assertNotNull(result);

        assertEquals(2, result.size());

        assertEquals("ARES 7021", result.get(0).getName());
        assertEquals("ARES 7031", result.get(1).getName());

        verify(restTemplate, times(1)).exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class);
    }

    @Test
    public void getModelsByCategoryIdWhenBodyIsNullShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL + categoryId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class))
                .thenReturn(ResponseEntity.ok(null));

        List<ModelDTO> result = modelService.getModelsByCategoryId(categoryId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getModelsByCategoryIdWhenApiThrowsExceptionShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL + categoryId);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, ModelDTO[].class))
                .thenThrow(new RestClientException("Connection refused"));

        List<ModelDTO> result = modelService.getModelsByCategoryId(categoryId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}