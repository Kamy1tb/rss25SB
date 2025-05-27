package fr.univrouen.rss25sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Rss25SbApplication {

    public static void main(String[] args) {
        // Set server port
        System.getProperties().put("server.port", 8080);
        SpringApplication.run(Rss25SbApplication.class, args);
    }

}
