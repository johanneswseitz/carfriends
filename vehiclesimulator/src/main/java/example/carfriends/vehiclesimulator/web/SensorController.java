package example.carfriends.vehiclesimulator.web;

import example.carfriends.vehiclesimulator.fake_sensor.FakeSensorRepository;
import example.carfriends.vehiclesimulator.fake_sensor.Vin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sensors")
public class SensorController {

    private FakeSensorRepository sensorRepository;

    public SensorController(FakeSensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping("/{vin}")
    public ModelAndView sensor(@PathVariable("vin") String vin) {
        var sensor = sensorRepository.findByVin(new Vin(vin)).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("sensor/sensor.html");
        modelAndView.addObject("vin", sensor.vin().vin());
        modelAndView.addObject("longitude", sensor.location().longitude());
        modelAndView.addObject("latitude", sensor.location().latitude());
        return modelAndView;
    }
}
