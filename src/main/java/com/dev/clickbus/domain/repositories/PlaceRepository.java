package com.dev.clickbus.domain.repositories;

import com.dev.clickbus.domain.models.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceRepository extends MongoRepository<Place, String> {
}
