package com.dev.clickbus.web.controllers;

import com.dev.clickbus.common.dtos.ErrorResponse;
import com.dev.clickbus.common.dtos.PlaceRequest;
import com.dev.clickbus.common.dtos.PlaceResponse;
import com.dev.clickbus.domain.models.Place;
import com.dev.clickbus.domain.repositories.PlaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PlaceControllerTest {

    @ServiceConnection
    private static final MongoDBContainer MONGODB_CONTAINER = new MongoDBContainer("mongo:latest");

    @Autowired
    private PlaceRepository repository;

    @Autowired
    private TestRestTemplate restClient;

    @BeforeAll
    static void setUp() {
        MONGODB_CONTAINER.start();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateAPlace() {
        PlaceRequest requestBody = new PlaceRequest("Blue Street", "Dream City", "NY");

        ResponseEntity<PlaceResponse> response = restClient
                .postForEntity("/api/v1/places", requestBody, PlaceResponse.class);

        assertAll(() -> {
            PlaceResponse responseBody = response.getBody();

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(responseBody);
            assertNotNull(responseBody.id());
            assertNotNull(responseBody.createdAt());
        });
    }

    @Test
    void shouldNotCreateAPlace_WhenPlaceNameIsInUse() {
        repository.save(new Place("Blue Street", "Dream City", "NY"));

        PlaceRequest requestBody = new PlaceRequest("Blue Street", "Dream City", "NY");

        ResponseEntity<ErrorResponse> response = restClient
                .postForEntity("/api/v1/places", requestBody, ErrorResponse.class);

        assertAll(() -> {
            ErrorResponse responseBody = response.getBody();

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(responseBody);
            assertEquals("Name constraint is already in use!", responseBody.message());

        });
    }

    @Test
    void shouldRetrieveAllPlaces_WhenFilterWasNotProvided() {
        repository.saveAll(List.of(
                new Place("Blue Street", "Dream City", "NY"),
                new Place("Green Street", "Dream City", "CA")
        ));

        ResponseEntity<List<PlaceResponse>> response = restClient.exchange(
                "/api/v1/places",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PlaceResponse>>() {
                });

        assertAll(() -> {
            List<PlaceResponse> responseBody = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(responseBody);
            assertEquals(2, responseBody.size());
        });
    }

    @Test
    void shouldRetrieveAPlace_WhenFilterWasProvidedAndHasPlacesThatMatchesWithFilter() {
        repository.saveAll(List.of(
                new Place("Blue Street", "Dream City", "NY"),
                new Place("Green Street", "Dream City", "CA")
        ));

        ResponseEntity<List<PlaceResponse>> response = restClient.exchange(
                "/api/v1/places?filter={filter}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PlaceResponse>>() {
                }, "Blue");

        assertAll(() -> {
            List<PlaceResponse> responseBody = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(responseBody);
            assertEquals(1, responseBody.size());
        });
    }

    @Test
    void shouldNotRetrievePlaces_WhenFilterWasProvidedAndDoesNotHavePlacesThatMatchesWithFilter() {
        repository.saveAll(List.of(
                new Place("Blue Street", "Dream City", "NY"),
                new Place("Green Street", "Dream City", "CA")
        ));

        ResponseEntity<List<PlaceResponse>> response = restClient.exchange(
                "/api/v1/places?filter={filter}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }, "Red");

        assertAll(() -> {
            List<PlaceResponse> responseBody = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(responseBody);
            assertTrue(responseBody.isEmpty());
        });
    }

    @Test
    void shouldUpdatePlace() {
        Place place = repository.save(new Place("Blue Street", "Dream City", "NY"));

        PlaceRequest requestBody = new PlaceRequest("Red Street", "Dream City", "CA");

        ResponseEntity<PlaceResponse> response = restClient.exchange("/api/v1/places/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(requestBody),
                PlaceResponse.class,
                place.getId());

        assertAll(() -> {
            PlaceResponse responseBody = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(responseBody);
            assertEquals("Red Street", responseBody.name());
            assertEquals(place.getId(), responseBody.id());
        });
    }

    @Test
    void shouldDeletePlace() {
        Place place = repository.save(new Place("Blue Street", "Dream City", "NY"));

        ResponseEntity<Void> response = restClient.exchange("/api/v1/places/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                place.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldNotDeletePlace_WhenIdWasNotFound() {
        ResponseEntity<ErrorResponse> response = restClient.exchange("/api/v1/places/{id}",
                HttpMethod.DELETE,
                null,
                ErrorResponse.class,
                "123");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}