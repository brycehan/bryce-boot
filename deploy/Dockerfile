FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

LABEL org.opencontainers.image.authors="Bryce Han"
ADD ./bryce-server/target/bryce-server-0.0.1-SNAPSHOT.jar /app.jar
WORKDIR /
# 设置时区
ENV TZ=Asia/Shanghai
EXPOSE 8090

ENTRYPOINT [\
    "java",\
    "-server",\
    "-Dserver.port=8090",\
    "-Dspring.profiles.active=prod",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-Dfile.encoding=UTF-8",\
    "-Duser.timezone=GMT+08",\
    "-XX:+HeapDumpOnOutOfMemoryError",\
    "-jar",\
    "/app.jar"\
]
