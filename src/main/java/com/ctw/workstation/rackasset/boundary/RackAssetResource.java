package com.ctw.workstation.rackasset.boundary;

import com.ctw.workstation.rackasset.dtos.RackAssetRequest;
import com.ctw.workstation.rackasset.service.RackAssetService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/racks/{rackId}/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RackAssetResource {
    @Inject
    RackAssetService service;

    @GET
    public Response index(@PathParam("rackId") Long rackId){
        return Response.status(Response.Status.OK)
                .entity(service.index(rackId))
                .build();
    }

    @POST
    public Response store(@PathParam("rackId") Long rackId, RackAssetRequest request){
        return Response.status(Response.Status.CREATED)
                .entity(service.store(rackId, request))
                .build();
    }

    @GET
    @Path("{assetId}")
    public Response show(@PathParam("rackId") Long rackId, @PathParam("assetId") Long assetId){
        return Response.status(Response.Status.OK)
                .entity(service.show(rackId, assetId))
                .build();
    }

    @PUT
    @Path("{assetId}")
    public Response update(@PathParam("rackId") Long rackId, @PathParam("assetId") Long assetId, RackAssetRequest request){
        return Response.status(Response.Status.OK)
                .entity(service.update(rackId, assetId, request))
                .build();
    }

    @DELETE
    @Path("{assetId}")
    public Response delete(@PathParam("rackId") Long rackId, @PathParam("assetId") Long assetId){
        service.delete(rackId, assetId);
        return Response.status(Response.Status.OK)
                .build();
    }

}
