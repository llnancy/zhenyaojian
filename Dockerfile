FROM openjdk:8u342-jre-slim-buster
ADD ./zhenyaojian-admin/target/zhenyaojian.jar /app.jar
ENV spring.profiles.active="pro"
RUN apt-get update \
    && apt-get install -y --no-install-recommends tzdata \
    && ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && dpkg-reconfigure -f noninteractive tzdata \
    && apt-get purge -y --auto-remove tzdata \
    && rm -rf /var/lib/apt/lists/*
LABEL maintainer=admin@lilu.org.cn
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--server.port=8080"]
