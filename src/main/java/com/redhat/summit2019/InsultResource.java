package com.redhat.summit2019;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.summit2019.model.Insult;
import com.redhat.summit2019.service.AdjectiveService;
import com.redhat.summit2019.service.NounService;

@Path("/")
@ApplicationScoped
public class InsultResource{

    @Inject @ConfigProperty(name="url.adjective")
    private String adjectiveURL;

    @Inject @ConfigProperty(name="url.noun")
    private String nounURL;

    @GET
    @Path("/insult")
    @Produces(MediaType.APPLICATION_JSON)
    public String getInsult() throws Exception {

        ResteasyClient nounClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget nounTarget = nounClient.target(nounURL);
        NounService nounService = nounTarget.proxy(NounService.class);

        ResteasyClient adjClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget adjTarget = adjClient.target(adjectiveURL);
        AdjectiveService adjService = adjTarget.proxy(AdjectiveService.class);

        return new Insult(adjService.getAdjective(),
            adjService.getAdjective(),
            nounService.getNoun()).toString();
    }

}