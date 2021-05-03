package ru.otus.homework.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DbHealthCheckActuator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        try {
            dataSource.getConnection();
            return Health.up()
                    .status(Status.UP)
                    .withDetail("message", "DB is up")
                    .build();
        } catch (SQLException e) {
            return Health.down(e)
                    .status(Status.DOWN)
                    .withDetail("message", "DB is down")
                    .build();
        }
    }
}
