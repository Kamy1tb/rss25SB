package fr.univrouen.rss25sb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class IndexController {
    @GetMapping("/")
    public String home(Model model) {
        // Ajoute des données au modèle
        model.addAttribute("projectName", "Projet XML RSS25");
        model.addAttribute("version", "1.0.0");
        model.addAttribute("developer", "Kamyl Taibi");
        model.addAttribute("universityLogo", "https://www.choisirlanormandie.fr/app/uploads/2024/08/logo-universite-de-rouen.png");
        return "landing"; // Nom du fichier Thymeleaf (index.html)
    }
}