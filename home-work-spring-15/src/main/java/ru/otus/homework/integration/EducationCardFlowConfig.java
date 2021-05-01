package ru.otus.homework.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.service.CourseSelectionService;
import ru.otus.homework.service.MentorAssignmentService;
import ru.otus.homework.service.StudentIdService;

@Configuration
@RequiredArgsConstructor
public class EducationCardFlowConfig {

    private final StudentIdService studentIdService;
    private final CourseSelectionService courseSelectionService;
    private final MentorAssignmentService mentorAssignmentService;

    @Bean
    public QueueChannel studentChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel educationCardChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow workFlow() {
        return IntegrationFlows.from("studentChannel")
                .handle(studentIdService, "registerStudent")
                .handle(courseSelectionService,"selectCoursesForStudent")
                .handle(mentorAssignmentService,"assignMentorFoStudent")
                .channel("educationCardChannel")
                .get();
    }

}
