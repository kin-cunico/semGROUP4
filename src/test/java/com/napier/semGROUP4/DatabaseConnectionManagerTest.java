package com.napier.semGROUP4;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

class DatabaseConnectionManagerTest {

    @Test
    void connectFailsWithInvalidUrlAfterConfiguredAttempts() {
        DatabaseConnectionManager manager = DatabaseConnectionManager.builder()
                .withUrl("jdbc:mysql://127.0.0.1:65000/world?allowPublicKeyRetrieval=true&useSSL=false")
                .withRetryAttempts(1)
                .withRetryDelay(Duration.ZERO)
                .build();

        IllegalStateException exception = assertThrows(IllegalStateException.class, manager::connect);
        assertTrue(exception.getCause() instanceof SQLException, "Expected root cause to be an SQLException.");
    }

    @Test
    void closeDoesNotThrowWhenConnectionNeverOpened() {
        DatabaseConnectionManager manager = DatabaseConnectionManager.builder()
                .withRetryAttempts(1)
                .withRetryDelay(Duration.ZERO)
                .build();

        assertDoesNotThrow(manager::close);
    }

    @Test
    void builderRejectsInvalidRetryAttempts() {
        DatabaseConnectionManager.Builder builder = DatabaseConnectionManager.builder();

        assertThrows(IllegalArgumentException.class, () -> builder.withRetryAttempts(0));
    }
}
