package com.dev.clickbus.common.dtos;

import jakarta.validation.constraints.NotBlank;

public record PlaceRequest(
        @NotBlank(message = "Name cannot be null or empty!") String name,
        @NotBlank(message = "City cannot be null or empty!") String city,
        @NotBlank(message = "State cannot be null or empty!") String state
) {
}
