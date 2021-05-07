package ru.otus.homework.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.domain.EducationCard;
import ru.otus.homework.domain.Student;

@MessagingGateway
public interface EducationCardForStudentGateway {
    @Gateway(requestChannel = "studentChannel", replyChannel = "educationCardChannel")
    EducationCard process(Student student);
}
