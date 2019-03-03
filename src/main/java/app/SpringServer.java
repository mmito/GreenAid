package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "app.controllers")
@ComponentScan(basePackages = "app.authentication")
public class SpringServer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringServer.class);
    }

    /**
     * Class that starts the client.
     * @param args arguments of the main method.
     */
    public static void main(String[] args) {

        SpringApplication.run(SpringServer.class, args);

    }
}