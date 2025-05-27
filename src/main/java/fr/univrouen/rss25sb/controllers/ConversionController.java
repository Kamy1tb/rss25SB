package fr.univrouen.rss25sb.controllers;

import fr.univrouen.rss25sb.service.FeedConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Contrôleur pour l'application de conversion
 */
@Controller
public class ConversionController {

    private final FeedConverterService feedConverterService;

    /**
     * Constructeur qui injecte le FeedConverterService
     * @param feedConverterService le service de conversion de flux
     */
    @Autowired
    public ConversionController(FeedConverterService feedConverterService) {
        this.feedConverterService = feedConverterService;
    }

    /**
     * Affiche l'interface de l'application de conversion
     * @param model le modèle auquel ajouter des attributs
     * @return le nom du template Thymeleaf
     */
    @GetMapping("/convert")
    public String convert(Model model) {
        // Ajouter des sources prédéfinies au modèle
        model.addAttribute("sources", new String[]{
            "Le Monde - International",
            "Fonction Publique - Actualités"
        });
        return "convert";
    }

    /**
     * Convertit un flux d'une source au format RSS25
     * @param source la source à partir de laquelle convertir
     * @return le flux converti au format RSS25
     */
    @PostMapping("/convert")
    @ResponseBody
    public String convertFeed(@RequestParam("source") String source) {
        try {
            // Utiliser le service pour convertir le flux
            return feedConverterService.convertFeed(source);
        } catch (Exception e) {
            // Retourner un message d'erreur
            return "Erreur lors de la conversion: " + e.getMessage();
        }
    }
}
