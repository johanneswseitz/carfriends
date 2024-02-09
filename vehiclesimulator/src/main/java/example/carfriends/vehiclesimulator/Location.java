package example.carfriends.vehiclesimulator;

public record Location(double latitude, double longitude) {
    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }
}
