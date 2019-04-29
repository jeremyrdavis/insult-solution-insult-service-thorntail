package com.redhat.summit2019;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


@RunWith(Arquillian.class)
@DefaultDeployment
public class InsultResourceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8081));

    @Test
    @RunAsClient
    public void testInsultResource(){

        stubFor(get(urlEqualTo("/api/adjective"))
                .inScenario("insult-scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"adjective\": \"puking\"}"))
                .willSetStateTo("initial insult retrieved"));

        stubFor(get(urlEqualTo("/api/adjective"))
                .inScenario("insult-scenario")
                .whenScenarioStateIs("initial insult retrieved")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"adjective\": \"beslubbering\"}"))
                .willSetStateTo("initial insult retrieved"));

        stubFor(get(urlPathMatching("/api/noun"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"noun\": \"pantaloon\"}")));

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080")
                .path("api").path("insult");

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
        String returnedInsult = response.readEntity(String.class);
        Assert.assertEquals(returnedInsult, "{\"insult\":\"Verily, ye be a puking, beslubbering pantaloon!\"}");
    }

}