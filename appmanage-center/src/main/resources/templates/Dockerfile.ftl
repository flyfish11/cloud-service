FROM openjdk:8

VOLUME /tmp

EXPOSE ${port}

ADD ./${service_name}.jar /${service_name}.jar

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /${service_name}.jar" ]
