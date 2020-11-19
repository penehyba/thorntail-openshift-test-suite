package io.thorntail.openshift.ts.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloResource {
    @GET
    public String hello() {
        return "Hello world";
    }
}