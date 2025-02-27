package com.ctw.workstation.rack.boundary;

import com.ctw.workstation.rack.dtos.RackRequest;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.enums.RackStatus;
import com.ctw.workstation.rack.repository.RackRepository;
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
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestHTTPEndpoint(RackResource.class)
class RackResourceTest {
    @Inject
    TeamRepository teamRepository;

    @Inject
    RackRepository rackRepository;

    private Long teamId;
    private Long existingRackId;

    @BeforeAll
    @Transactional
    void setup() {
        Team team = new Team("Racers", "Aerodynamics", "Lisbon");
        teamRepository.persistAndFlush(team);

        Rack rack = new Rack("12345", RackStatus.AVAILABLE, team, "Lisbon");
        rackRepository.persistAndFlush(rack);

        teamId = team.getId();
        existingRackId = rack.getId();
    }

    @Test
    @Order(1)
    void creates_a_new_rack() {
        RackRequest request = new RackRequest("123ABC", "AVAILABLE", teamId, "Lisbon");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("defaultLocation", equalTo("Lisbon"))
                .body("serialNumber", equalTo("123ABC"));
    }

    @Test
    @Order(2)
    void fetches_all_racks() {
        given()
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(2)
    void fetches_all_racks_by_status() {
        given()
                .when()
                .queryParam("status", "available")
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    @Order(3)
    void fetches_a_single_rack() {
        given()
                .when()
                .pathParams("rackId", existingRackId)
                .get("/{rackId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(existingRackId.intValue()));
    }

    @Test
    @Order(3)
    void fails_to_fetch_non_existing_rack() {
        given()
                .when()
                .pathParams("rackId", 999999)
                .get("/{rackId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .onFailMessage("That rack doesn't exist");
    }

    @Test
    @Order(4)
    void updates_a_rack() {
        RackRequest request = new RackRequest("789","BOOKED", teamId, "Porto");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .pathParams("rackId", existingRackId)
                .put("/{rackId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("serialNumber", equalTo("789"))
                .body("status", equalTo("Booked"));
    }

    @Test
    @Order(5)
    void deletes_a_rack() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("rackId", existingRackId)
                .delete("/{rackId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    @Order(5)
    void fails_to_delete_non_existing_rack() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("rackId", 999999)
                .delete("/{rackId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
