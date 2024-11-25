# 使用官方的 OpenJDK 21 作为基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 将项目的 jar 文件复制到容器中, 并重命名为 app.jar. PS: 修改代码后要记得重新打包
COPY ./target/LifeRecordsServer.jar /app.jar

# 设置环境变量
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_USER=your_default_username
ENV MYSQL_PASSWORD=your_default_password

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# 暴露应用程序的端口
EXPOSE 8090

# 运行应用程序
ENTRYPOINT ["java", "-jar", "/app.jar"]