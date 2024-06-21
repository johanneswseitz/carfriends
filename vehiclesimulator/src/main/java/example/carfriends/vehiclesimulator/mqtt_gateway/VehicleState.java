package example.carfriends.vehiclesimulator.mqtt_gateway;

public record VehicleState(String parkedState, String doorsState, int totalKM, String lan, String lon) {
}
