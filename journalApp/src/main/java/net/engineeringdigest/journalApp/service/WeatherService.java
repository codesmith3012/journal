package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_KEY = "dc5b094025e17d359f20544f1df9a705";
    private static final String API_URL = "https://api.weatherstack.com/current?access_key={apiKey}&query={city}";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        // Try fetching data from Redis cache
        WeatherResponse cachedWeather = redisService.get("weather_" + city, WeatherResponse.class);
        if (cachedWeather != null) {
            return cachedWeather;
        }

        // If not in cache, make an API request
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, null, WeatherResponse.class, API_KEY, city
        );

        WeatherResponse weatherResponse = response.getBody();
        if (weatherResponse != null) {
            // Store the response in Redis for 5 minutes (300 seconds)
            redisService.set("weather_" + city, weatherResponse, 300L);
        }

        return weatherResponse;
    }
}
