FROM tomcat:9.0-jdk11-openjdk

# Dọn dẹp folder gốc của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Đưa folder giao diện webapp vào làm trang chủ
COPY webapp /usr/local/tomcat/webapps/ROOT

# Đưa driver kết nối Database vào đúng chỗ để không bị lỗi 500
COPY webapp/WEB-INF/lib/mysql-connector-java-5.1.49.jar /usr/local/tomcat/lib/

EXPOSE 8080
CMD ["catalina.sh", "run"]
