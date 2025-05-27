package fr.univrouen.rss25sb.controllers;

import fr.univrouen.rss25sb.util.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the transfer application
 */
@Controller
public class TransferController {

    private final XmlValidator xmlValidator;

    /**
     * Constructor that injects the XmlValidator
     * @param xmlValidator the XML validator
     */
    @Autowired
    public TransferController(XmlValidator xmlValidator) {
        this.xmlValidator = xmlValidator;
    }

    /**
     * Display the transfer application interface
     * @return the name of the Thymeleaf template
     */
    @GetMapping("/transfer")
    public String transfer() {
        return "transfer";
    }
}