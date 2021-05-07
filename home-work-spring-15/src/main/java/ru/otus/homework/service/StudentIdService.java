package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.EducationCard;
import ru.otus.homework.domain.Student;

import java.util.Random;
import java.util.UUID;

@Service
public class StudentIdService {
    public EducationCard registerStudent(Student student) throws InterruptedException {

        System.out.println("Регистрируем студента в международной базе и присваиваем ему уникальный номер");
        Thread.sleep(new Random().nextInt(5) * 1000);

        EducationCard educationCard = new EducationCard();
        educationCard.setStudent(student);
        educationCard.setId(UUID.randomUUID().toString());

        System.out.println("Студент зарегистрирован c id " + educationCard.getId());

        return educationCard;
    }
}
