package example.carfriends.vehiclesimulator.routing;

import example.carfriends.vehiclesimulator.Location;

public interface GeocodingService {
    GeocodingResult geocode(String address);

    sealed interface GeocodingResult {
        record Success(Location location) implements GeocodingResult {
        }

        record Failure() implements GeocodingResult {
        }
    }
}
