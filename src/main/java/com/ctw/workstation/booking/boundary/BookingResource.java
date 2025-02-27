package com.ctw.workstation.booking.boundary;

import com.ctw.workstation.booking.dtos.BookingRequestDTO;
import com.ctw.workstation.booking.service.BookingService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import java.util.UUID;


@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    BookingService service;

    @GET
    public Response index(){
        MDC.put("traceId", UUID.randomUUID().toString());
        Log.info("index() resource");
        return Response.status(Response.Status.OK)
                .entity(service.index())
                .build();
    }

    @POST
    public Response store(BookingRequestDTO request){
        return Response.status(Response.Status.CREATED)
                .entity(service.store(request))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, BookingRequestDTO request){
        return Response.status(Response.Status.CREATED)
                .entity(service.update(id, request))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.status(Response.Status.OK)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response show(@PathParam("id") Long id){
        return Response.status(Response.Status.OK)
                .entity(service.show(id))
                .build();
    }
}
