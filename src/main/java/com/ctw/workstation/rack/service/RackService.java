package com.ctw.workstation.rack.service;

import com.ctw.workstation.booking.boundary.BookingResource;
import com.ctw.workstation.rack.dtos.RackRequest;
import com.ctw.workstation.rack.dtos.RackResponse;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.enums.RackStatus;
import com.ctw.workstation.rack.repository.RackRepository;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Singleton
public class RackService {

    Logger logger = Logger.getLogger(RackService.class);

    @Inject
    RackRepository rackRepository;

    @Inject
    TeamRepository teamRepository;

    public List<RackResponse> index() {
        return index(null);
    }

    public List<RackResponse> index(String status) {
        List<Rack> racks;
        if (status == null || status.isBlank()) {
            racks = rackRepository.listAll();
        } else {
            racks = rackRepository.findByStatus(RackStatus.getByValue(status));
        }
        return racks.stream().map(this::buildResponseDTO).toList();
    }


    public RackResponse show(Long id){
        Rack rack = rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("That rack doesn't exist"));

        return buildResponseDTO(rack);
    }

    @Transactional
    public RackResponse update(Long id, RackRequest request){

        Rack rack = rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("That rack doesn't exist"));

        rack.setStatus(RackStatus.getByValue(request.status()));

        if (request.teamId() != null) {
            Team team = teamRepository.findByIdOptional(id)
                    .orElseThrow(() -> new NotFoundException("That team doesn't exist"));
            rack.setTeam(team);
        }

        rack.setDefaultLocation(request.defaultLocation());
        rack.setSerialNumber(request.serialNumber());
        rackRepository.persistAndFlush(rack);
        return buildResponseDTO(rack);
    }

    @Transactional
    public boolean delete(Long id){
        Rack rack = rackRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("That rack doesn't exist"));

        if(!rack.getBookings().isEmpty()){
            throw new BadRequestException("You can't delete this rack because it has bookings");
        }

        return rackRepository.deleteById(id);
    }

    @Transactional
    public RackResponse store(RackRequest request){
        Team team = teamRepository.findById(request.teamId());

        if(team == null){
            throw new WebApplicationException("The team does not exist", Response.Status.BAD_REQUEST);
        }

        Rack rack = new Rack(request.serialNumber(), RackStatus.AVAILABLE, team, request.defaultLocation());

        rackRepository.persistAndFlush(rack);

        return buildResponseDTO(rack);
    }

    private RackResponse buildResponseDTO(Rack rack){
        return new RackResponse(
                rack.getId(),
                rack.getSerialNumber(),
                rack.getStatus().getName(),
                rack.getTeam().getName() + " - " + rack.getTeam().getDefaultLocation(),
                rack.getCreatedAt(),
                rack.getModifiedAt(),
                rack.getDefaultLocation()
        );
    }
}
