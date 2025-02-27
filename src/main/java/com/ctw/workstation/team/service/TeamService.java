package com.ctw.workstation.team.service;

import com.ctw.workstation.team.dtos.TeamRequestDTO;
import com.ctw.workstation.team.dtos.TeamResponseDTO;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;

@Singleton
public class TeamService {
    @Inject
    TeamRepository teamRepository;

    @Inject
    Logger logger;

    @Transactional
    public TeamResponseDTO add(TeamRequestDTO request) {
        if (teamRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("A team with this name already exists");
        }
        Team team = new Team(request.name(), request.product(), request.defaultLocation());
        teamRepository.persistAndFlush(team);
        return buildResponseDTO(team);
    }

    private TeamResponseDTO buildResponseDTO(Team team) {
        return new TeamResponseDTO(team.getId(), team.getName(), team.getProduct(), team.getDefaultLocation(), team.getCreatedAt(), team.getModifiedAt());
    }

    public List<TeamResponseDTO> getAll() {
        return teamRepository.findAll()
                .stream()
                .map(this::buildResponseDTO)
                .toList();
    }

    @Transactional
    public TeamResponseDTO update(Long id, TeamRequestDTO request) {
        Team team = teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("That team doesn't exist"));

        if (!team.getName().equals(request.name()) && teamRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("A team with this name already exists");
        }

        team.setName(request.name());
        team.setProduct(request.product());
        team.setDefaultLocation(request.defaultLocation());
        teamRepository.persistAndFlush(team);
        return buildResponseDTO(team);
    }

    @Transactional
    public boolean delete(Long id) {
        boolean deleted = teamRepository.deleteById(id);
        if (deleted) {
          logger.info("Team deleted with ID: " + id);
          return true;
        }

        throw new NotFoundException("That team doesn't exist");
    }

    public TeamResponseDTO show(Long id) {
        Team team = teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("That team doesn't exist"));
        return buildResponseDTO(team);
    }
}
