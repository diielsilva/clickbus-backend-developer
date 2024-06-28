package com.dev.clickbus.services;

import com.dev.clickbus.dtos.PlaceRequest;
import com.dev.clickbus.dtos.PlaceResponse;
import com.dev.clickbus.mappers.ModelMapper;
import com.dev.clickbus.models.Place;
import com.dev.clickbus.repositories.PlaceRepository;
import com.dev.clickbus.utils.SlugGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository repository;
    private final ModelMapper mapper;
    private final SlugGenerator slugGen;

    public PlaceService(PlaceRepository repository, ModelMapper mapper, SlugGenerator slugGen) {
        this.repository = repository;
        this.mapper = mapper;
        this.slugGen = slugGen;
    }

    public PlaceResponse save(PlaceRequest request) {
        Place place = mapper.toModel(request);
        String slug = slugGen.getByPlaceName(place.getName());

        place.setSlug(slug);
        place.setCreatedAt(LocalDateTime.now());

        repository.save(place);

        return mapper.toResponse(place);
    }

    public List<PlaceResponse> findAll(String filter) {
        List<Place> places = repository.findAll();

        if (filter != null) {
            places = places
                    .stream()
                    .filter(place -> place.getName().contains (filter))
                    .toList();
        }

        return places
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

}
