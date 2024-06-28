package com.dev.clickbus.controllers;

import com.dev.clickbus.dtos.PlaceRequest;
import com.dev.clickbus.dtos.PlaceResponse;
import com.dev.clickbus.services.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/places")
public class PlaceController {

    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PlaceResponse> save(@RequestBody @Valid PlaceRequest request) {
        PlaceResponse response = service.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> findAll(@RequestParam(required = false) String filter) {
        List<PlaceResponse> responses = service.findAll(filter);
        return ResponseEntity
                .ok(responses);
    }

}
