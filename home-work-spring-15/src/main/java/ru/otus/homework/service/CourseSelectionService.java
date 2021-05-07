package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.EducationCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.otus.homework.utils.RandomUtils.randomString;

@Service
public class CourseSelectionService {

    public EducationCard selectCoursesForStudent(EducationCard educationCard) throws InterruptedException {

        System.out.println("Подбираем студенту курсы");
        Thread.sleep(new Random().nextInt(5) * 1000);

        int numberOfCourses = new Random().nextInt(10);
        List<String> courses = new ArrayList<>();
        for (int i = 0; i < numberOfCourses; i++)
            courses.add(randomString());
        educationCard.setCourses(courses);

        System.out.println("Курсы назначены:");
        courses.forEach(System.out::println);

        return educationCard;
    }

}
