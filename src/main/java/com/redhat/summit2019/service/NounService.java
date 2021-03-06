package com.redhat.summit2019.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.summit2019.model.Noun;

/**
 * @author Ken Finnigan
 */
@Path("/")
public interface NounService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/noun")
    Noun getNoun();

}
