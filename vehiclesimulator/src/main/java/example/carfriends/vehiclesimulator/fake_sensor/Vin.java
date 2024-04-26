package example.carfriends.vehiclesimulator.fake_sensor;

import java.util.ArrayList;
import java.util.List;

public record Vin(String vin) {

    public static final List<String> FAKE_VINS = new ArrayList<>(List.of("1C3BC55D0CG133270",
            "1GKLVNED8AJ200101", "3WKDD40X0CF701574", "JH4DA1850GS002669",
            "JH4DB7650RS000250", "JT2BF22K6Y0283641", "KMHDU4AD5AU136970"));

    public static Vin randomVin()  {
        return new Vin(FAKE_VINS.removeFirst());
    }
}
