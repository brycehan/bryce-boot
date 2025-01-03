FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

LABEL org.opencontainers.image.authors="Bryce Han"
COPY ./bryce-boot-server/target/bryce-boot-server-0.0.1-SNAPSHOT.jar /app.jar

WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai

EXPOSE 8090

ENTRYPOINT java \
	-server \
	-Xms1g \
	-Xmx1g \
	-XX:MetaspaceSize=128m \
	-XX:MaxMetaspaceSize=320m \
	-XX:+HeapDumpOnOutOfMemoryError \
	-Djava.security.egd=file:/dev/./urandom \
	-Dfile.encoding=UTF-8 \
	-Duser.timezone=${TZ} \
	-Dserver.port=8090 \
	-Dspring.profiles.active=dev \
	-jar \
	/app.jar
