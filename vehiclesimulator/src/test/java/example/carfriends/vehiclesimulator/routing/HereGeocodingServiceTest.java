package example.carfriends.vehiclesimulator.routing;

import com.github.tomakehurst.wiremock.WireMockServer;
import example.carfriends.vehiclesimulator.Location;
import example.carfriends.vehiclesimulator.routing.GeocodingService.GeocodingResult.Failure;
import example.carfriends.vehiclesimulator.routing.GeocodingService.GeocodingResult.Success;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HereGeocodingServiceTest {

    public static final String API_KEY = "SECRET";
    private WireMockServer wireMockServer;
    private HereMapsConfiguration.HereMapsProperties configuration;
    private HereGeocodingService hereGeocodingService;

    @BeforeAll
    void setup() {
        this.wireMockServer = new WireMockServer();
        this.wireMockServer.start();
        this.configuration = new HereMapsConfiguration.HereMapsProperties(API_KEY,
                new HereMapsConfiguration.Geocoding(this.wireMockServer.baseUrl()));
        this.hereGeocodingService = new HereGeocodingService(this.configuration);
    }

    @Test
    void geocode_emptyResult_returnsFailure() {
        // arrange
        var address = "Invalidenstr+117+Berlin";
        this.wireMockServer.stubFor(get(urlPathEqualTo("/v1/geocode"))
                .withQueryParam("apiKey", equalTo(API_KEY))
                .withQueryParam("q", equalTo(address))
                .willReturn(okForContentType("application/json",
                        """
                                {
                                  "items": []
                                }
                                """)));

        // act
        var result = hereGeocodingService.geocode(address);

        // assert
        assertThat(result).isEqualTo(new Failure());
    }

    @Test
    void geocode_resultWithPoint_returnsSuccess() {
        // arrange
        var address = "Invalidenstr+117+Berlin";
        this.wireMockServer.stubFor(get(urlPathEqualTo("/v1/geocode"))
                .withQueryParam("apiKey", equalTo(API_KEY))
                .withQueryParam("q", equalTo(address))
                .willReturn(okForContentType("application/json",
                        """
                                {
                                  "items": [
                                    {
                                      "title": "Invalidenstraße 117, 10115 Berlin, Deutschland",
                                      "resultType": "houseNumber",
                                      "position": {
                                        "lat": 52.53041,
                                        "lng": 13.38527
                                        }
                                    }
                                  ]
                                }
                                """)));

        // act
        var result = hereGeocodingService.geocode(address);

        // assert
        assertThat(result).isEqualTo(new Success(Location.of(52.53041d, 13.38527d)));
    }
}