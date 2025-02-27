package com.ctw.workstation.teammember.boundary;

import com.ctw.workstation.teammember.dtos.TeamMemberRequestDTO;
import com.ctw.workstation.teammember.service.TeamMemberService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/teams/{teamId}/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamMemberResource {

    @Inject
    TeamMemberService service;

    @GET
    public Response index(@PathParam("teamId") Long teamId){
        return Response.status(Response.Status.OK)
                .entity(service.index(teamId))
                .build();
    }

    @POST
    public Response store(@PathParam("teamId") Long teamId, TeamMemberRequestDTO request){
        return Response.status(Response.Status.CREATED)
                .entity(service.add(request))
                .build();
    }

    @GET
    @Path("{id}")
    public Response show(@PathParam("teamId") Long teamId, @PathParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity(service.show(teamId, id))
                .build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("teamId") Long teamId, @PathParam("id") Long id, TeamMemberRequestDTO request){
        return Response.status(Response.Status.OK)
                .entity(service.update(teamId, id, request))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("teamId") Long teamId, @PathParam("id") Long id) {
        boolean deleted = service.delete(teamId, id);
        return deleted ? Response.noContent().build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

}
