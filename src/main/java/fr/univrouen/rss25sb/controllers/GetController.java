package fr.univrouen.rss25sb.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class GetController {
    @GetMapping("/resume")
    public String getListRSSinXML() {
        return "Envoi de la liste des flux RSS";
    }
    @GetMapping("/guid")
    public String getRSSinXML(
            @RequestParam(value = "guid") String texte) {
        return ("Détail du flux RSS demandé : " + texte);
    }
    @GetMapping("/test")
    public String getListRSSinXML(
            @RequestParam(value="nb") Integer nb,
            @RequestParam(value="search") String search

    ) {
        return "Test :\n guid= "+nb+"\n search= "+search;
    }
}