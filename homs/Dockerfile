FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="godbyul <skypower4203@gmail.com>"
LABEL version="1.0"
COPY ./build/libs/homs-0.0.1-SNAPSHOT.jar /root
ARG BUILD_PORT=8088
ARG BUILD_PROFILE=test
ENV TZ=Asia/Seoul
EXPOSE ${BUILD_PORT}
ENV APP_PROFILE=${BUILD_PROFILE}
WORKDIR /root
CMD ["java", "-jar", "homs-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=${APP_PROFILE}"]