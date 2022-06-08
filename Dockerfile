
FROM openjdk:11
RUN mkdir /app
COPY build/libs/ExchangeRateVisualizationService-0.0.1.jar /app
WORKDIR /app
CMD java -jar ExchangeRateVisualizationService-0.0.1.jar
EXPOSE 8080
