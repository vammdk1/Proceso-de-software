package es.deusto.testing.cliente;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import es.deusto.spq.client.PictochatntClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;


public class ClientTest {

	private static WireMockServer wireMockServer;

    @BeforeClass
    public static void setup() {
        WireMockConfiguration wireMockConfig = WireMockConfiguration.options().port(8080);
        wireMockServer = new WireMockServer(wireMockConfig);
        wireMockServer.start();

        configureFor("localhost", 8080);

        stubFor(post(urlEqualTo("/register"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"token\": \"my-token\"}")));
    }

    @Test
    public void testRegister() {
        PictochatntClient.init("localhost", "8080");

        boolean result = PictochatntClient.register("user", "password");
        System.out.println(result);
        //assertTrue(result);
        //assertEquals("my-token", PictochatntClient.getToken());
    }

}
