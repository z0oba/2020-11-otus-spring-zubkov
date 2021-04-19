package ru.otus.homework.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
@Getter
@Setter
public class Book {
    @Id
    private String id;
    @JsonProperty("name")
    @Field("name")
    private String name;

    @JsonProperty("author")
    @Field("author")
    private String author;

    @JsonProperty("genre")
    @Field("genre")
    private String genre;

    @DBRef
    @Field("comments")
    private List<Comment> comments = new ArrayList<>();

    public Book (String name, String author, String genre){
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book (String name, String author, String genre, List<Comment> comments){
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }
}
