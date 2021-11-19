package com.project.m2dfs.project.meteo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class ApplicationController {
    @Autowired
    RestTemplate restTemplate;

    String query;

    @ApiOperation(value = "Get city code by cityName", response = ApplicationController.class, tags = "getCityCode")
    @RequestMapping(value = "/getCityCode/{query}", method = RequestMethod.GET)
    public String getCityCode(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=2ISwGQTvWNt4926KkCQnSbTUXDoWTXCc&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}