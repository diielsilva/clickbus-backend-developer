package com.dev.clickbus.utils;

import org.springframework.stereotype.Component;

@Component
public class SlugGenerator {

    public String getByPlaceName(String name) {
        return name
                .replace(" ", "-")
                .toLowerCase();
    }

}
