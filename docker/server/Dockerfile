# 基础镜像
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

# 镜像标签
LABEL org.opencontainers.image.authors="Bryce Han"
# 复制jar包
COPY ./jar/bryce-boot-server-0.0.1-SNAPSHOT.jar /app.jar
# 设置工作目录
WORKDIR /

# 设置时区
ENV TZ=Asia/Shanghai
# 暴露端口
EXPOSE 8090
# 启动容器
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
	-Dspring.profiles.active=prod \
	-jar \
	/app.jar
