package com.ctw.workstation.team.boundary;

import com.ctw.workstation.team.dtos.TeamRequestDTO;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.team.repository.TeamRepository;
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
@TestHTTPEndpoint(TeamResource.class)
class TeamResourceTest {

    @Inject
    TeamRepository teamRepository;

    private Long existingTeamId;

    @BeforeAll
    @Transactional
    void setup() {
        Team team = new Team("Racers", "Aerodynamics", "Lisbon");
        teamRepository.persist(team);
        existingTeamId = team.getId();
    }

    @Order(1)
    @Test
    @DisplayName("Creates a new team")
    void creates_a_new_team() {
        TeamRequestDTO request = new TeamRequestDTO("BikeLife", "Motorad", "Porto");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo("BikeLife"))
                .body("product", equalTo("Motorad"))
                .body("defaultLocation", equalTo("Porto"));
    }

    @Order(1)
    @Test
    @DisplayName("Fails if new team has an existing name")
    void throws_exception_if_duplicated_name_on_create() {
        TeamRequestDTO request = new TeamRequestDTO("Racers", "Panels", "Braga");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .onFailMessage("A team with this name already exists");
    }

    @Test
    @Order(2)
    @DisplayName("Fetches an existing team")
    void fetches_an_existing_team() {
    given()
        .when()
        .pathParams("teamId", existingTeamId)
        .get("/{teamId}")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("id", comparesEqualTo(existingTeamId.intValue()))
        .body("name", equalTo("Racers"))
        .body("product", equalTo("Aerodynamics"))
        .body("defaultLocation", equalTo("Lisbon"));
    }

    @Test
    @Order(3)
    @DisplayName("Fails to fetch a team that doesn't exists in database")
    void fails_to_fetch_an_invalid_team_id() {
        given()
                .when()
                .pathParams("teamId", 9191919)
                .get("/{teamId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .onFailMessage("That team doesn't exist");
    }

    @Test
    @DisplayName("Updates an existing team")
    @Order(3)
    void update_team() {
        TeamRequestDTO request = new TeamRequestDTO("Updated Team", "Updated Product", "HQ3");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .pathParams("teamId", existingTeamId)
                .put("/{teamId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Updated Team"))
                .body("product", equalTo("Updated Product"))
                .body("defaultLocation", equalTo("HQ3"));
    }

    @Test
    @Order(3)
    @DisplayName("Fails to update team if name is already persisted in another team")
    void not_update_if_duplicated_name() {
        TeamRequestDTO newTeam = new TeamRequestDTO("Beta Team", "Product B", "HQ2");
        given().contentType(ContentType.JSON).body(newTeam).post();

        TeamRequestDTO request = new TeamRequestDTO("Beta Team", "Product B", "HQ2");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/" + existingTeamId)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .onFailMessage("A team with this name already exists");
    }

    @Test
    @Order(4)
    @DisplayName("Deletes an existing team")
    void delete_existing_team() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/" + existingTeamId)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(4)
    @DisplayName("Fails to delete a non-existing team")
    void return_not_found_when_deleting_non_existing_team() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/99999")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .onFailMessage("That team doesn't exist");
    }
}
