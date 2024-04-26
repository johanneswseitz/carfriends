package example.carfriends.vehiclesimulator.fake_sensor;

import java.util.List;
import java.util.Optional;

public interface FakeSensorRepository {
    void createSensor(FakeSensor sensor);

    Optional<FakeSensor> findByVin(Vin vin);

    List<FakeSensor> all();
}
