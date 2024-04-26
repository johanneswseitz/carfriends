package example.carfriends.vehiclesimulator.fake_sensor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class FakeSensorInMemoryRepository implements FakeSensorRepository {

    private HashMap<Vin, FakeSensor> sensors = new HashMap<>();

    @Override
    public void createSensor(FakeSensor sensor) {
        this.sensors.put(sensor.vin(), sensor);
    }

    @Override
    public Optional<FakeSensor> findByVin(Vin vin) {
        return Optional.ofNullable(sensors.get(vin));
    }

    @Override
    public List<FakeSensor> all() {
        return sensors.values().stream().toList();
    }
}
