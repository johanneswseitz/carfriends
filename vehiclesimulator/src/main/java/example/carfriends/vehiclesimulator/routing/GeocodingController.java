package example.carfriends.vehiclesimulator.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/geocoding")
public class GeocodingController {

    private GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping()
    public ModelAndView index() {
        return new ModelAndView("geocoding.html");
    }

    @PostMapping("query")
    public ModelAndView query(@RequestParam(value = "address") String address) {
        var result = geocodingService.geocode(address);
        ModelAndView modelAndView = new ModelAndView("geocoding.html");
        var resultString = (switch (result) {
            case GeocodingService.GeocodingResult.Success success: yield success.location();
            case GeocodingService.GeocodingResult.Failure ignored: yield "Nicht gefunden";
        });
        modelAndView.addObject("address", address);
        modelAndView.addObject("result", resultString);
        return modelAndView;
    }

}
