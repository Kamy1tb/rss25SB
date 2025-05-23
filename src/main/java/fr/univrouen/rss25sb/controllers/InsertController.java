package fr.univrouen.rss25sb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the insertion page
 */
@Controller
public class InsertController {

    /**
     * Display the insertion page
     * @return the name of the Thymeleaf template
     */
    @GetMapping("/insert")
    public String insert() {
        return "insert";
    }
}