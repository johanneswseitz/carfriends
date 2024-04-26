package example.carfriends.vehiclesimulator;

import example.carfriends.vehiclesimulator.fake_sensor.FakeSensor;
import example.carfriends.vehiclesimulator.fake_sensor.FakeSensorRepository;
import example.carfriends.vehiclesimulator.fake_sensor.Vin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationListener<ApplicationReadyEvent> {

    private final FakeSensorRepository sensorRepository;

    public StartupRunner(FakeSensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.sensorRepository.createSensor(
                new FakeSensor(Vin.randomVin(), Location.of(51.10027497852672, 6.8850961475061325)));
    }
}
