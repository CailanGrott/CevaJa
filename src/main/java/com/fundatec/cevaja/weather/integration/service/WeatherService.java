package com.fundatec.cevaja.weather.integration.service;


import com.fundatec.cevaja.weather.integration.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;

    @Value("${weather-external-api}")
    private String uri;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public WeatherResponse buscarTemperatura(){
        String urlCompleta = this.uri;
        return this.restTemplate.getForObject(urlCompleta , WeatherResponse.class);
    }

}
