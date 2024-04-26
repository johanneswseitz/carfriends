package example.carfriends.vehiclesimulator.fake_sensor;

import example.carfriends.vehiclesimulator.Location;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakeSensorInMemoryRepositoryTest {

    FakeSensorRepository repository = new FakeSensorInMemoryRepository();

    @Test
    void createdSensors_canBeFound() {
        var sensor = new FakeSensor(Vin.randomVin(), Location.of(0, 0));
        var sensor2 = new FakeSensor(Vin.randomVin(), Location.of(1, 2));

        repository.createSensor(sensor);
        repository.createSensor(sensor2);

        assertThat(repository.all()).containsExactlyInAnyOrder(sensor, sensor2);
    }

    @Test
    void findByVin_returnsEmpty() {
        var sensor = new FakeSensor(Vin.randomVin(), Location.of(0, 0));

        repository.createSensor(sensor);

        assertThat(repository.findByVin(new Vin("unknown-vin"))).isEmpty();
    }

    @Test
    void findByVin_returnsSensor() {
        var sensor = new FakeSensor(Vin.randomVin(), Location.of(0, 0));

        repository.createSensor(sensor);

        assertThat(repository.findByVin(sensor.vin())).contains(sensor);
    }
}