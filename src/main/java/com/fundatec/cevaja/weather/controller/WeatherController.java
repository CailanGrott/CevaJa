package com.fundatec.cevaja.weather.controller;


import com.fundatec.cevaja.weather.integration.response.WeatherResponse;
import com.fundatec.cevaja.weather.integration.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clima")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * Endpoint para obter a temperatura em Porto Alegre.
     *
     * @return ResponseEntity com status 200 (OK) e o objeto WeatherResponse contendo as informações climáticas atuais em Porto Alegre.
     */

    @GetMapping
    public ResponseEntity<WeatherResponse> tempPoa() {
        return ResponseEntity.ok(this.weatherService.buscarTemperatura());
    }
}
