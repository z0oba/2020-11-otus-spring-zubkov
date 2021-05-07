package ru.otus.homework.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework.repo.BookRepository;

@Component
@RequiredArgsConstructor
public class LibraryCapacityHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        if (!bookRepository.findAll().isEmpty()) {
            return Health.up()
                    .status(Status.UP)
                    .withDetail("message", "Book repo isn`t empty")
                    .build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Book repo is empty")
                    .build();
        }
    }
}
