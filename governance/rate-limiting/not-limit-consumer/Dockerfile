FROM openjdk:17

WORKDIR /home/apps/
ADD target/not-limit-consumer-1.0-SNAPSHOT.jar .
ADD start.sh .

ENTRYPOINT ["sh", "/home/apps/start.sh"]