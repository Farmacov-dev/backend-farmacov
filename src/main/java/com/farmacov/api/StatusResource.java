package com.farmacov.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/status")
public class StatusResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response status() {
        return Response.ok("{\"status\":\"ok\",\"app\":\"farmacov-backend\"}").build();
    }
}