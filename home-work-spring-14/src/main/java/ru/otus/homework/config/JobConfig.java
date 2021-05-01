package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.BookJpa;
import ru.otus.homework.dto.CommentJpa;
import ru.otus.homework.processor.BookProcessor;
import ru.otus.homework.processor.CommentProcessor;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final String JOB_NAME = "MongoToSql";

    private final JobBuilderFactory jobBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final BookProcessor bookProcessor;
    private final CommentProcessor commentProcessor;

    @StepScope
    @Bean
    public MongoItemReader<Book> bookMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookReader")
                .template(mongoTemplate)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookJpa> bookItemProcessor() {
        return bookProcessor;
    }

    @StepScope
    @Bean
    public JpaItemWriter<BookJpa> bookJpaItemWriter() {
        return new JpaItemWriterBuilder<BookJpa>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step bookStep() {
        return stepBuilderFactory.get("bookStep")
                .<Book, BookJpa>chunk(1)
                .reader(bookMongoItemReader(mongoTemplate))
                .processor(bookItemProcessor())
                .writer(bookJpaItemWriter())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> commentMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Comment>()
                .name("commentReader")
                .template(mongoTemplate)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, CommentJpa> commentItemProcessor() {
        return commentProcessor;
    }

    @StepScope
    @Bean
    public JpaItemWriter<CommentJpa> commentJpaItemWriter() {
        return new JpaItemWriterBuilder<CommentJpa>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step commentStep() {
        return stepBuilderFactory.get("commentStep")
                .<Comment, CommentJpa>chunk(5)
                .reader(commentMongoItemReader(mongoTemplate))
                .processor(commentItemProcessor())
                .writer(commentJpaItemWriter())
                .build();
    }


    @Bean
    public Job mongoToMySql() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(bookStep())
                .next(commentStep())
                .build();
    }
}
