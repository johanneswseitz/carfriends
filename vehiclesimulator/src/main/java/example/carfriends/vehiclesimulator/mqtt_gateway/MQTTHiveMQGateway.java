package example.carfriends.vehiclesimulator.mqtt_gateway;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class MQTTHiveMQGateway {

    private final Mqtt5BlockingClient client;

    private final String username;
    private final String password;

    public MQTTHiveMQGateway(Mqtt5BlockingClient client,
                             @Value("${hive-mq.username}") String username,
                             @Value("${hive-mq.password}") String password) {
        this.client = client;
        this.username = username;
        this.password = password;
    }


    void publishVehicleState(String parkedState, String lockedState, String lon, String lat, String totalKM) {
        client.connectWith().simpleAuth()
                .username(username)
                .password(password.getBytes(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send();
        String currentDateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/last-changed")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload( currentDateTime.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/parked-state")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(parkedState.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/locked-state")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(lockedState.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/position/lon")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(lon.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/position/lat")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(lat.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.publishWith().topic("vehicle/1GKLVNED8AJ200101/total-km")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(totalKM.getBytes(StandardCharsets.UTF_8))
                .retain(true)
                .send();
        client.disconnect();
    }
}
