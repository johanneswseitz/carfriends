package example.carfriends.vehiclesimulator;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class VehicleSimulatorConfig {

    @Bean
    public Mqtt5BlockingClient mqtt5BlockingClient(@Value("${hive-mq.broker-url}") String brokerUrl) {
        return Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(brokerUrl)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();
    }
}
