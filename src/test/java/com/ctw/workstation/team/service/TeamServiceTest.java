package com.ctw.workstation.team.service;

import com.ctw.workstation.config.CtwAcademyTestProfile;
import com.ctw.workstation.team.dtos.TeamRequestDTO;
import com.ctw.workstation.team.dtos.TeamResponseDTO;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@TestProfile(CtwAcademyTestProfile.class)
class TeamServiceTest {
    @Inject
    TeamService teamService;

    @InjectSpy
    TeamRepository teamRepository;

    @Test
    @DisplayName("Create a team with error")
    void create_team_error() {
        TeamRepository teamRepositoryMock = Mockito.mock(TeamRepository.class);
        QuarkusMock.installMockForType(teamRepositoryMock, TeamRepository.class);

        Mockito.doThrow(new IllegalStateException("ERROR"))
            .when(teamRepositoryMock)
                .persistAndFlush(Mockito.any(Team.class));

        TeamRequestDTO teamRequestDTO = new TeamRequestDTO("Wizards", "Wind Wizards", "Lisbon");

        assertThatThrownBy(() -> teamService.add(teamRequestDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("ERROR");
    }

    @Test
    @DisplayName("Create a team")
    void create_team(){
        TeamRequestDTO teamRequestDTO = new TeamRequestDTO("Wizards", "Wind Wizards", "Lisbon");
        TeamResponseDTO createdTeam = teamService.add(teamRequestDTO);

        assertThat(createdTeam)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "modifiedAt")
                .isEqualTo(teamRequestDTO);
    }
}