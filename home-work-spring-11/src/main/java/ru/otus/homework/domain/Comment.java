package ru.otus.homework.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
@Getter
@Setter
public class Comment {


    @Id
    private String id;
    @JsonProperty("text")
    @Field("text")
    private String text;

//    @DBRef
//    private Book book;

    public Comment(String text /*, Book book*/) {
        this.text = text;
//        this.book = book;
    }
}
