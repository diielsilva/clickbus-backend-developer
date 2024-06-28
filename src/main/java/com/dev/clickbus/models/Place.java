package com.dev.clickbus.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Place(
        @Id
        String id,
        @Indexed(unique = true)
        String name,
        String slug,
        String city,
        String state
) {
}
