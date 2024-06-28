package com.dev.clickbus.mappers;

import com.dev.clickbus.dtos.PlaceRequest;
import com.dev.clickbus.dtos.PlaceResponse;
import com.dev.clickbus.models.Place;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public Place toModel(PlaceRequest request) {
        return new Place(
                request.name(),
                request.city(),
                request.state()
        );
    }

    public PlaceResponse toResponse(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getSlug(),
                place.getCity(),
                place.getState(),
                place.getCreatedAt(),
                place.getUpdatedAt()
        );
    }


}
