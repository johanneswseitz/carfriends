package example.carfriends.vehiclesimulator.routing;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HereMapsConfiguration.HereMapsProperties.class)
public class HereMapsConfiguration {

    @ConfigurationProperties("here-maps")
    public record HereMapsProperties(
            String apiKey,
            Geocoding geocoding) {
    }

    public record Geocoding(String baseUrl) {
    }
}
