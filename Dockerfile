FROM openjdk:8u342-jre-slim-buster
ADD ./zhenyaojian-admin/target/zhenyaojian.jar /app.jar
CMD ["--server.port=8080"]
ENV spring.profiles.active="pro"
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata
LABEL maintainer=admin@lilu.org.cn
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
