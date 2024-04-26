package example.carfriends.vehiclesimulator.web;

import example.carfriends.vehiclesimulator.fake_sensor.FakeSensorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    private FakeSensorRepository sensorRepository;

    public MainController(FakeSensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;

    }

    @GetMapping()
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index.html");
        modelAndView.addObject("sensors", sensorRepository.all());
        return modelAndView;
    }
}
