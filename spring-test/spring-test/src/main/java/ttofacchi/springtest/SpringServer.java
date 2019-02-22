package ttofacchi.springtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="ttofacchi.controllers")
@ComponentScan(basePackages="ttofacchi.authentication")
public class SpringServer {
    public static void main(String[] args) {

        SpringApplication.run(SpringServer.class, args);

    }
}