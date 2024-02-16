package example.carfriends.vehiclesimulator;

import example.carfriends.vehiclesimulator.fake_sensor.Vin;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VinTest {

    @Test
    public void testRandomVinGeneration(){
        assertThat(Vin.randomVin()).isEqualTo(new Vin("1C3BC55D0CG133270"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("1GKLVNED8AJ200101"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("3WKDD40X0CF701574"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("JH4DA1850GS002669"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("JH4DB7650RS000250"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("JT2BF22K6Y0283641"));
        assertThat(Vin.randomVin()).isEqualTo(new Vin("KMHDU4AD5AU136970"));
        assertThatThrownBy(() -> Vin.randomVin()).isInstanceOf(IndexOutOfBoundsException.class);
    }
}
