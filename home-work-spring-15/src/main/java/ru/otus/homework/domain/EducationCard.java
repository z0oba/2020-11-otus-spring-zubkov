package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EducationCard {
    private Student student;
    private String id;
    private String mentor;
    private List<String> courses;
}
