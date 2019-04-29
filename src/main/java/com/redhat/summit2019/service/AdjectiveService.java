package com.redhat.summit2019.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.summit2019.model.Adjective;

/**
 * @author Ken Finnigan
 */
@Path("/")
public interface AdjectiveService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/adjective")
    Adjective getAdjective();
}
