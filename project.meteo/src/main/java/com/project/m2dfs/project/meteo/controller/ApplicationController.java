package com.project.m2dfs.project.meteo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
public class ApplicationController {

    static ObjectMapper mapper = new ObjectMapper();

    public String apikey = "oDJ13QACVPNW3HtX1w8tnqbPCMrZ3Vzx";

    @Autowired
    RestTemplate restTemplate;

    String query;

    // Get City Code
    @ApiOperation(value = "Get city code by cityName", response = ApplicationController.class, tags = "getCityCode")
    @RequestMapping(value = "/getCityCode/{query}", method = RequestMethod.GET)
    public String getCityCode(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apikey + "&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }

    // Get City Weather with Code
    @ApiOperation(value = "Get city weather by cityKey", response = ApplicationController.class, tags = "getCityWeather")
    @RequestMapping(value = "/getCityWeather/{key}", method = RequestMethod.GET)
    public String getCityWeather(@PathVariable String key) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=" + apikey + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, key).getBody();
        return response;
    }

    // Get City Weather with Name
    @ApiOperation(value = "Get city weather by cityName", response = ApplicationController.class, tags = "getCityWeatherByCityName")
    @RequestMapping(value = "/getCityWeatherByCityName/{query}", method = RequestMethod.GET)
    public String getCityWeatherByCityName(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apikey + "&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();

        String json = response;
        System.out.println(json);
        json = json.substring(1, json.length() - 1);
        System.out.println(json);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);
        System.out.println(jsonJavaRootObject.get("Key"));

        String result = (String) jsonJavaRootObject.get("Key");

        String finalResponse = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/" + result + "?apikey=" + apikey + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, result).getBody();

        return finalResponse;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}