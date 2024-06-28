package com.dev.clickbus.repositories;

import com.dev.clickbus.models.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceRepository extends MongoRepository<Place, String> {
}
