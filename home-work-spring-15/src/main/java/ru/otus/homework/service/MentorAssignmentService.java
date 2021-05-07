package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.EducationCard;

import java.util.Random;

import static ru.otus.homework.utils.RandomUtils.randomString;

@Service
public class MentorAssignmentService {
    public EducationCard assignMentorFoStudent(EducationCard educationCard) throws InterruptedException {

        System.out.println("Назначаем студенту наставника");
        Thread.sleep(new Random().nextInt(5) * 1000);

        educationCard.setMentor(randomString());

        System.out.println("Назначен наставник " + educationCard.getMentor());

        return educationCard;
    }

}
