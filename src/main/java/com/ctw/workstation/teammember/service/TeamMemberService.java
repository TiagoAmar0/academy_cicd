package com.ctw.workstation.teammember.service;

import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
import com.ctw.workstation.teammember.dtos.TeamMemberRequestDTO;
import com.ctw.workstation.teammember.dtos.TeamMemberResponseDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.repository.TeamMemberRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;

@Singleton
public class TeamMemberService {

    @Inject
    TeamRepository teamRepository;

    @Inject
    TeamMemberRepository teamMemberRepository;

    public List<TeamMemberResponseDTO> index(Long teamId) {
        Team team = getTeam(teamId);

        return team.getTeamMembers()
                .stream()
                .map(this::buildResponseDTO)
                .toList();
    }

    @Transactional
    public TeamMemberResponseDTO add(TeamMemberRequestDTO request){
        Team team = getTeam(request.teamId());

        if (teamMemberRepository.findByCtwId(request.ctwId()).isPresent()) {
            throw new BadRequestException("A team member with this CTW ID already exists");
        }

        TeamMember teamMember = new TeamMember(request.ctwId(), team, request.name());
        teamMemberRepository.persistAndFlush(teamMember);

        return buildResponseDTO(teamMember);
    }

    private TeamMemberResponseDTO buildResponseDTO(TeamMember teamMember){
        return new TeamMemberResponseDTO(
                teamMember.getId(),
                teamMember.getName(),
                teamMember.getCtwId(),
                teamMember.getCreatedAt(),
                teamMember.getModifiedAt(),
                teamMember.getTeam().getName()
        );
    }

    private TeamMember getTeamMemberFromTeam(Team team, Long id) {
        return team.getTeamMembers().stream()
                .filter(tm -> tm.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Team member not found"));
    }

    private Team getTeam(Long id) {
        return teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Team not found"));
    }

    public TeamMemberResponseDTO show(Long teamId, Long id) {
        Team team = getTeam(teamId);
        TeamMember teamMember = getTeamMemberFromTeam(team, id);
        return buildResponseDTO(teamMember);
    }

    @Transactional
    public TeamMemberResponseDTO update(Long teamId, Long id, TeamMemberRequestDTO request) {
        Team team = getTeam(teamId);
        TeamMember teamMember = getTeamMemberFromTeam(team, id);

        if (!Objects.equals(teamId, request.teamId())) {
            team = getTeam(request.teamId());
        }

        if (!teamMember.getCtwId().equals(request.ctwId()) &&
                teamMemberRepository.findByCtwId(request.ctwId()).isPresent()) {
            throw new BadRequestException("A team member with this CTW ID already exists");
        }

        teamMember.setName(request.name());
        teamMember.setCtwId(request.ctwId());
        teamMember.setTeam(team);
        teamMemberRepository.persistAndFlush(teamMember);

        return buildResponseDTO(teamMember);
    }


    @Transactional
    public boolean delete(Long teamId, Long id) {
        Team team = getTeam(teamId);
        TeamMember teamMember = getTeamMemberFromTeam(team, id);
        return teamMemberRepository.deleteById(teamMember.getId());
    }
}
