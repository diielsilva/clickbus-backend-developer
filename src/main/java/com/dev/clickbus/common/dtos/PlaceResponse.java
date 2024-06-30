package com.dev.clickbus.common.dtos;

import java.time.LocalDateTime;

public record PlaceResponse(
        String id,
        String name,
        String slug,
        String city,
        String state,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
