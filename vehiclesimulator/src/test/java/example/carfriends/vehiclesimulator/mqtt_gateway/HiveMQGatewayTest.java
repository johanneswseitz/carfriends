package example.carfriends.vehiclesimulator.mqtt_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HiveMQGatewayTest {

    @Autowired
    MQTTHiveMQGateway gateway;

    @Test
    void test_publishing_vehicle_status() {
        String parkedState = "PARKED";
        String lockedState = "LOCKED";
        String lon = "51.10027497852672";
        String lat = "6.8850961475061325";
        String totalKM = "123456";

        gateway.publishVehicleState(parkedState, lockedState, lon, lat, totalKM);
    }

}
