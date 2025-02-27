package com.ctw.workstation.teammember.boundary;

import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
import com.ctw.workstation.teammember.dtos.TeamMemberRequestDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.repository.TeamMemberRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestHTTPEndpoint(TeamMemberResource.class)
class TeamMemberResourceTest {

    @Inject
    TeamRepository teamRepository;

    @Inject
    TeamMemberRepository teamMemberRepository;

    private Long teamId;
    private Long existingMemberId;

    @BeforeAll
    @Transactional
    void setup() {
        Team team = new Team("Racers", "Aerodynamics", "Lisbon");
        teamRepository.persist(team);
        teamId = team.getId();

        TeamMember member = new TeamMember("CTW123", team, "António Mendes");
        teamMemberRepository.persist(member);
        existingMemberId = member.getId();
    }

    @Test
    @Order(1)
    void creates_a_new_team_member() {
        TeamMemberRequestDTO request = new TeamMemberRequestDTO("António Mendes", "CTW456", teamId);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .pathParams("teamId", teamId)
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo("António Mendes"))
                .body("ctwId", equalTo("CTW456"));
    }

    @Test
    @Order(2)
    void fetches_all_team_members() {
        given()
                .when()
                .pathParams("teamId", teamId)
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(2)
    void fetches_a_single_team_member() {
        given()
                .when()
                .pathParams("id", existingMemberId)
                .pathParams("teamId", teamId)
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", comparesEqualTo(existingMemberId.intValue()))
                .body("name", equalTo("António Mendes"));
    }

    @Test
    @Order(2)
    void fails_to_fetch_non_existing_team_member() {
        given()
                .when()
                .pathParams("id", 999999)
                .pathParams("teamId", teamId)
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Order(3)
    void updates_a_team_member() {
        TeamMemberRequestDTO request = new TeamMemberRequestDTO("Marisa Liz","CTW789", teamId);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .pathParams("id", existingMemberId)
                .pathParams("teamId", teamId)
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Marisa Liz"))
                .body("ctwId", equalTo("CTW789"));
    }

    @Test
    @Order(4)
    void deletes_a_team_member() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("teamId", teamId)
                .pathParams("id", existingMemberId)
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @Order(4)
    void fails_to_delete_non_existing_team_member() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("teamId", teamId)
                .pathParams("id", 999999)
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
