package example.carfriends.vehiclesimulator.web;

import example.carfriends.vehiclesimulator.Location;
import example.carfriends.vehiclesimulator.fake_sensor.FakeSensor;
import example.carfriends.vehiclesimulator.fake_sensor.FakeSensorInMemoryRepository;
import example.carfriends.vehiclesimulator.fake_sensor.FakeSensorRepository;
import example.carfriends.vehiclesimulator.fake_sensor.Vin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
@Import(FakeSensorInMemoryRepository.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FakeSensorRepository sensorRepository;

    @Test
    void shouldReturnIndex_andListAllSensors() throws Exception {
        var sensor1 = new FakeSensor(Vin.randomVin(), Location.of(0, 0));
        var sensor2 = new FakeSensor(Vin.randomVin(), Location.of(10, 150));
        sensorRepository.createSensor(sensor1);
        sensorRepository.createSensor(sensor2);

        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"))
                .andExpect(model().attribute("sensors", containsInAnyOrder(sensor1, sensor2)));
    }
}