package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {

    private Current current;

    @Data
    public static class Current {
        private int temperature;
        private int feelslike;
    }
}
