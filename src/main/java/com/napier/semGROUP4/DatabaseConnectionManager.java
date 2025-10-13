package com.napier.semGROUP4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Centralised manager for establishing and closing database connections.
 * Designed to encapsulate retry behaviour, configuration, and driver management.
 */
public final class DatabaseConnectionManager implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static final AtomicBoolean DRIVER_LOADED = new AtomicBoolean(false);

    private static final String DEFAULT_JDBC_URL =
            "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "semgroup4";
    private static final int DEFAULT_RETRY_ATTEMPTS = 10;
    private static final Duration DEFAULT_RETRY_DELAY = Duration.ofSeconds(3);

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final int retryAttempts;
    private final Duration retryDelay;
    private Connection connection;

    private DatabaseConnectionManager(Builder builder) {
        this.jdbcUrl = builder.jdbcUrl;
        this.username = builder.username;
        this.password = builder.password;
        this.retryAttempts = builder.retryAttempts;
        this.retryDelay = builder.retryDelay;
    }

    /**
     * Creates a builder pre-populated with sensible defaults.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builds a manager using environment variables with fallbacks to defaults.
     */
    public static DatabaseConnectionManager fromEnvironment() {
        Builder builder = builder()
                .withUrl(resolveJdbcUrl())
                .withUsername(envOrDefault("DB_USER", DEFAULT_USERNAME))
                .withPassword(envOrDefault("DB_PASSWORD", DEFAULT_PASSWORD));

        builder.withRetryAttempts(parsePositiveInt("DB_RETRY_ATTEMPTS", DEFAULT_RETRY_ATTEMPTS));
        builder.withRetryDelay(Duration.ofMillis(parsePositiveLong("DB_RETRY_DELAY_MS",
                DEFAULT_RETRY_DELAY.toMillis())));

        return builder.build();
    }

    /**
     * Obtains an open connection, retrying according to configuration if necessary.
     *
     * @return A live JDBC connection.
     * @throws IllegalStateException if the connection cannot be established.
     */
    public synchronized Connection connect() {
        loadDriverIfRequired();

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Existing connection check failed, attempting to reconnect.", e);
        }

        SQLException lastFailure = null;
        for (int attempt = 1; attempt <= retryAttempts; attempt++) {
            try {
                connection = DriverManager.getConnection(jdbcUrl, username, password);
                final int attemptNumber = attempt;
                LOGGER.log(Level.INFO, () ->
                        String.format(Locale.ROOT, "Connected to database (attempt %d/%d).", attemptNumber, retryAttempts));
                return connection;
            } catch (SQLException ex) {
                lastFailure = ex;
                final int attemptNumber = attempt;
                LOGGER.log(Level.WARNING, ex, () ->
                        String.format(Locale.ROOT, "Failed to connect (attempt %d/%d).", attemptNumber, retryAttempts));
                sleepQuietly(retryDelay);
            }
        }

        throw new IllegalStateException(
                String.format(Locale.ROOT, "Unable to establish database connection after %d attempts.", retryAttempts),
                lastFailure);
    }

    /**
     * Retrieves the current connection, connecting if one is not already available.
     */
    public synchronized Connection getConnection() {
        return connect();
    }

    /**
     * Indicates whether a live connection is currently held.
     */
    public synchronized boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.FINE, "isConnected check failed.", e);
            return false;
        }
    }

    /**
     * Closes the managed connection if one exists.
     */
    @Override
    public synchronized void close() {
        if (connection == null) {
            return;
        }

        try {
            if (!connection.isClosed()) {
                connection.close();
                LOGGER.info("Database connection closed.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing database connection.", e);
        } finally {
            connection = null;
        }
    }

    private static void sleepQuietly(Duration delay) {
        if (delay.isZero() || delay.isNegative()) {
            return;
        }

        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Retry interrupted while waiting to reconnect.", e);
        }
    }

    private static String envOrDefault(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    private static int parsePositiveInt(String key, int fallback) {
        String raw = System.getenv(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }

        try {
            int parsed = Integer.parseInt(raw);
            if (parsed < 1) {
                throw new IllegalArgumentException();
            }
            return parsed;
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, () ->
                    String.format(Locale.ROOT,
                            "Invalid value '%s' for %s. Using fallback %d.", raw, key, fallback));
            return fallback;
        }
    }

    private static long parsePositiveLong(String key, long fallback) {
        String raw = System.getenv(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }

        try {
            long parsed = Long.parseLong(raw);
            if (parsed < 1) {
                throw new IllegalArgumentException();
            }
            return parsed;
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, () ->
                    String.format(Locale.ROOT,
                            "Invalid value '%s' for %s. Using fallback %d.", raw, key, fallback));
            return fallback;
        }
    }

    private static String resolveJdbcUrl() {
        String directUrl = System.getenv("DB_URL");
        if (directUrl != null && !directUrl.isBlank()) {
            return directUrl;
        }

        String host = envOrDefault("DB_HOST", "db");
        String port = envOrDefault("DB_PORT", "3306");
        String database = envOrDefault("DB_NAME", "world");
        String options = envOrDefault("DB_OPTIONS", "allowPublicKeyRetrieval=true&useSSL=false");

        String normalisedOptions = options.isBlank()
                ? ""
                : (options.startsWith("?") ? options : "?" + options);

        return String.format(Locale.ROOT, "jdbc:mysql://%s:%s/%s%s", host, port, database, normalisedOptions);
    }

    private static void loadDriverIfRequired() {
        if (DRIVER_LOADED.compareAndSet(false, true)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                LOGGER.fine("MySQL JDBC driver loaded.");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Could not load SQL driver.", e);
            }
        }
    }

    /**
     * Fluent builder for database connection managers.
     */
    public static final class Builder {
        private String jdbcUrl = DEFAULT_JDBC_URL;
        private String username = DEFAULT_USERNAME;
        private String password = DEFAULT_PASSWORD;
        private int retryAttempts = DEFAULT_RETRY_ATTEMPTS;
        private Duration retryDelay = DEFAULT_RETRY_DELAY;

        private Builder() {
        }

        public Builder withUrl(String jdbcUrl) {
            this.jdbcUrl = Objects.requireNonNull(jdbcUrl, "jdbcUrl must not be null.");
            return this;
        }

        public Builder withUsername(String username) {
            this.username = Objects.requireNonNull(username, "username must not be null.");
            return this;
        }

        public Builder withPassword(String password) {
            this.password = Objects.requireNonNull(password, "password must not be null.");
            return this;
        }

        public Builder withRetryAttempts(int retryAttempts) {
            if (retryAttempts < 1) {
                throw new IllegalArgumentException("retryAttempts must be >= 1.");
            }
            this.retryAttempts = retryAttempts;
            return this;
        }

        public Builder withRetryDelay(Duration retryDelay) {
            Objects.requireNonNull(retryDelay, "retryDelay must not be null.");
            if (retryDelay.isNegative()) {
                throw new IllegalArgumentException("retryDelay must not be negative.");
            }
            this.retryDelay = retryDelay;
            return this;
        }

        public DatabaseConnectionManager build() {
            return new DatabaseConnectionManager(this);
        }
    }
}
