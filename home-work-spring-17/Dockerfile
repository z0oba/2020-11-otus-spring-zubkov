FROM openjdk:15

ARG PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY . $PROJECT_DIR

RUN chmod +x ./gradlew; \
	./gradlew bootJar;  \
	chmod +x ./wait-for-it.sh

FROM openjdk:15

ARG PROJECT_DIR=/opt/project
ARG APP_DIR=/opt/app
ARG APP_NAME=home-work-spring-17-0.0.1-SNAPSHOT.jar

RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR

COPY --from=0 $PROJECT_DIR/wait-for-it.sh $APP_DIR/
COPY --from=0 $PROJECT_DIR/build/libs/$APP_NAME $APP_DIR/app.jar

EXPOSE 8080
CMD ["java","-jar","/opt/app/app.jar"]