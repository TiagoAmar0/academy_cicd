package com.ctw.workstation.team.boundary;

import com.ctw.workstation.team.dtos.TeamRequestDTO;
import com.ctw.workstation.team.service.TeamService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {
    @Inject
    TeamService service;

    @GET
    public Response index() {
        return Response.ok(service.getAll()).build();
    }

    @POST
    public Response store(TeamRequestDTO request){
        try {
            return Response.status(Response.Status.CREATED)
                    .entity(service.add(request))
                    .build();
        } catch (IllegalArgumentException exception){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") Long id){
        return Response.ok(service.show(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TeamRequestDTO request){
        try {
            return Response.ok(service.update(id, request))
                    .build();
        } catch (IllegalArgumentException exception){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
