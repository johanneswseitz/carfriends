package example.carfriends.vehiclesimulator.routing;

import example.carfriends.vehiclesimulator.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Service
public class HereGeocodingService implements GeocodingService {

    private final RestClient restClient;
    private HereMapsConfiguration.HereMapsProperties hereMapsProperties;

    public HereGeocodingService(HereMapsConfiguration.HereMapsProperties hereMapsProperties) {
        this.hereMapsProperties = hereMapsProperties;
        System.out.println(hereMapsProperties);
        this.restClient = RestClient.builder().baseUrl(hereMapsProperties.geocoding().baseUrl())
                .defaultHeader(USER_AGENT, "vehicle-simulator")
                .defaultUriVariables(Map.of("apiKey", hereMapsProperties.apiKey()))
                .build();
    }

    @Override
    public GeocodingResult geocode(String address) {
        return toGeocodingResult(restClient
                .get()
                .uri("/v1/geocode?q={query}&apiKey={apiKey}", Map.of("query", address))
                .retrieve()
                .toEntity(GeocodeResponse.class).getBody());
    }

    private GeocodingResult toGeocodingResult(GeocodeResponse response) {
        if (response == null || response.items.isEmpty()) {
            return new GeocodingResult.Failure();
        }
        var position = response.items.getFirst().position;
        return new GeocodingResult.Success(Location.of(position.lat, position.lng));
    }

    record GeocodeResponse(List<Item> items) {
        record Item(String resultType, Position position) {
            record Position(double lng, double lat) {
            }
        }
    }

}
