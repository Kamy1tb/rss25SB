package fr.univrouen.rss25sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Rss25SbApplication {

    public static void main(String[] args) {
        System.getProperties().put("server.port", 4789);
        SpringApplication.run(Rss25SbApplication.class, args);
    }

}
