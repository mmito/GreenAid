package app.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="app.controllers")
@ComponentScan(basePackages="app.authentication")
public class SpringServer {
    public static void main(String[] args) {

        SpringApplication.run(SpringServer.class, args);

    }
}