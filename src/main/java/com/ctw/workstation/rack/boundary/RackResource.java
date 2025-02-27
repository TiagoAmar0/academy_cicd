package com.ctw.workstation.rack.boundary;

import com.ctw.workstation.rack.dtos.RackRequest;
import com.ctw.workstation.rack.service.RackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/racks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RackResource {

    @Inject
    RackService service;

    @POST
    public Response store(RackRequest request){
        return Response.status(Response.Status.CREATED)
                .entity(service.store(request))
                .build();
    }

    @GET
    public Response index(@QueryParam("status") String status){
        if(status == null || status.isBlank()){
            return Response.status(Response.Status.OK)
                    .entity(service.index())
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(service.index(status))
                .build();
    }

    @GET
    @Path("/{rackId}")
    public Response show(@PathParam("rackId") Long rackId){
        return Response.status(Response.Status.OK)
                .entity(service.show(rackId))
                .build();
    }

    @PUT
    @Path("/{rackId}")
    public Response update(@PathParam("rackId") Long rackId, RackRequest request){
        return Response.status(Response.Status.OK)
                .entity(service.update(rackId, request))
                .build();
    }

    @DELETE
    @Path("/{rackId}")
    public Response delete(@PathParam("rackId") Long rackId){
        try {
            boolean deleted = service.delete(rackId);
            return deleted ? Response.noContent().build() :
                    Response.status(Response.Status.NOT_FOUND).build();
        } catch (BadRequestException exception){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        }
    }
}
