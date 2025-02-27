package com.ctw.workstation.external;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/external/hello")
@RegisterRestClient(configKey = "external-api")
public interface ExternalApi {

    @GET
    ExternalResponse hello();

    @POST
    ExternalResponse hello(ExternalRequest externalRequest);
}
