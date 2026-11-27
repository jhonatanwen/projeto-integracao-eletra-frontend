package com.eletra.service;

import com.eletra.dto.LineDTO;
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
public class LineServiceTest {
    private static final String API_URL = LineService.API_URL;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LineService lineService;

    @Test
    public void getAllLinesShouldReturnListOfLines() throws Exception {
        LineDTO cronos = new LineDTO(1, "Cronos");
        LineDTO ares = new LineDTO(2, "Ares");
        LineDTO[] body = {cronos, ares};

        URI expectedUri = new URI(API_URL);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, LineDTO[].class))
                .thenReturn(ResponseEntity.ok(body));

        List<LineDTO> result = lineService.getAllLines();

        assertNotNull(result);

        assertEquals(2, result.size());

        assertEquals("Cronos", result.get(0).getName());
        assertEquals("Ares", result.get(1).getName());

        verify(restTemplate, times(1)).exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, LineDTO[].class);
    }

    @Test
    public void getAllLinesWhenBodyIsNullShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, LineDTO[].class))
                .thenReturn(ResponseEntity.ok(null));

        List<LineDTO> result = lineService.getAllLines();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllLinesWhenApiThrowsExceptionShouldReturnEmptyList() throws Exception {
        URI expectedUri = new URI(API_URL);
        when(restTemplate.exchange(expectedUri, HttpMethod.GET, HttpEntity.EMPTY, LineDTO[].class))
                .thenThrow(new RestClientException("Connection refused"));

        List<LineDTO> result = lineService.getAllLines();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}