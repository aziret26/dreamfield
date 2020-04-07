FROM openjdk:8-jdk

COPY build/libs/dreamfield-*.jar /home/dreamfield.jar

CMD java -server \
    -jar -Dspring.profiles.active=docker /home/dreamfield.jar