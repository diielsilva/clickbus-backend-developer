package com.dev.clickbus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest
class ClickbusApplicationTests {

    @ServiceConnection
    private static final MongoDBContainer MONGODB_CONTAINER = new MongoDBContainer("mongo:latest");

    @BeforeAll
    static void setUp() {
        MONGODB_CONTAINER.start();
    }

    @Test
    void contextLoads() {
    }

}
