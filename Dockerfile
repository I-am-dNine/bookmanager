# 使用官方 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 指定工作目录
WORKDIR /app

# 复制 jar 文件
COPY target/bookmanager-0.0.1-SNAPSHOT.jar app.jar

# 暴露容器内部的端口（视你的应用端口设定，默认 8080）
EXPOSE 8080

# 运行 jar 文件
ENTRYPOINT ["java", "-jar", "app.jar"]