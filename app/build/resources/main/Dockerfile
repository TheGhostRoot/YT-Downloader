FROM ubuntu
WORKDIR /home
COPY * /home/
RUN apt update -y && apt install openjdk-17-jdk openjdk-17-jre -y
RUN chmod +x /home/*
CMD ["java", "-jar", "/home/app-0.1.jar"]
