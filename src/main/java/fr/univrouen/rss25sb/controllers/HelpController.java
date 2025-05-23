package fr.univrouen.rss25sb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the help page
 */
@Controller
public class HelpController {

    /**
     * Display the help page with information about the REST service
     * @param model the model to add attributes to
     * @return the name of the Thymeleaf template
     */
    @GetMapping("/help")
    public String help(Model model) {
        List<Map<String, String>> endpoints = new ArrayList<>();

        // Home page
        Map<String, String> home = new HashMap<>();
        home.put("url", "/");
        home.put("method", "GET");
        home.put("operation", "Affiche la page d'accueil");
        home.put("return", "Format HTML ou XHTML valide");
        home.put("description", "Affiche la page d'accueil du projet avec les informations suivantes :<br>" +
                "• Nom du projet<br>" +
                "• Numéro de version<br>" +
                "• Nom & Prénom du (ou des) développeur(se)(s)<br>" +
                "• Logo de l'Université de Rouen");
        endpoints.add(home);

        // Help page
        Map<String, String> help = new HashMap<>();
        help.put("url", "/help");
        help.put("method", "GET");
        help.put("operation", "Affiche la page contenant les informations d'aide");
        help.put("return", "Format HTML ou XHTML valide");
        help.put("description", "Affiche la liste des opérations gérées par le service REST.<br>" +
                "Pour chacune des opérations proposées par le service, sont affichés :<br>" +
                "• URL<br>" +
                "• Méthode<br>" +
                "• Résumé de l'opération (informations et formats attendus, format de retour, ...)");
        endpoints.add(help);

        // List of articles in XML
        Map<String, String> resumeXml = new HashMap<>();
        resumeXml.put("url", "/rss25SB/resume/xml");
        resumeXml.put("method", "GET");
        resumeXml.put("operation", "Affiche la liste des articles stockés");
        resumeXml.put("return", "Flux XML");
        resumeXml.put("description", "Liste résumée des articles présents dans la base.<br>" +
                "Pour chaque article, ne seront affichées que les informations suivantes :<br>" +
                "• id<br>" +
                "• date<br>" +
                "• guid (url de l'article)");
        endpoints.add(resumeXml);

        // List of articles in HTML
        Map<String, String> resumeHtml = new HashMap<>();
        resumeHtml.put("url", "/rss25SB/resume/html");
        resumeHtml.put("method", "GET");
        resumeHtml.put("operation", "Affiche la liste des articles stockés");
        resumeHtml.put("return", "Page HTML (ou XHTML)");
        resumeHtml.put("description", "Liste des articles présents dans la base.<br>" +
                "Mêmes informations que précédemment mais au format d'une page HTML,<br>" +
                "les informations seront présentées dans un tableau.<br>" +
                "Remarque : Il doit être possible d'accéder à la page de l'article en cliquant sur l'url.");
        endpoints.add(resumeHtml);

        // Article details in XML
        Map<String, String> detailXml = new HashMap<>();
        detailXml.put("url", "/rss25SB/resume/xml/{id}");
        detailXml.put("method", "GET");
        detailXml.put("operation", "Affiche le contenu complet de l'article dont l'identifiant est {id}");
        detailXml.put("return", "Flux XML conforme au schéma xsd comprenant un seul article (partie Item du flux)");
        detailXml.put("description", "Intégralité de l'article dont l'identifiant est fourni par son {id}<br>" +
                "Si l'identifiant est incorrect, retour d'un message d'erreur au format XML contenant :<br>" +
                "- {id} → numéro de l'identifiant demandé<br>" +
                "- status → ERROR");
        endpoints.add(detailXml);

        // Article details in HTML
        Map<String, String> detailHtml = new HashMap<>();
        detailHtml.put("url", "/rss25SB/html/{id}");
        detailHtml.put("method", "GET");
        detailHtml.put("operation", "Affiche le contenu complet de l'article dont l'identifiant est {id}");
        detailHtml.put("return", "Page HTML (ou XHTML)");
        detailHtml.put("description", "Intégralité de l'article dont l'identifiant est fourni par son {id}<br>" +
                "Si l'identifiant est incorrect, retour d'un message d'erreur au format HTML contenant :<br>" +
                "- {id} → numéro de l'identifiant demandé<br>" +
                "- status → ERROR");
        endpoints.add(detailHtml);

        // Add article
        Map<String, String> insert = new HashMap<>();
        insert.put("url", "/rss25SB/insert");
        insert.put("method", "POST");
        insert.put("operation", "Ajoute un article dans la base");
        insert.put("return", "Flux XML");
        insert.put("description", "Flux XML décrivant le flux rss25SB à ajouter, conforme au schéma xsd.<br>" +
                "Le flux reçu est validé par le schéma XSD de définition rss25SB<br>" +
                "Si le flux est déjà présent, (même titre et date) alors une indication d'erreur est retournée.<br>" +
                "Si l'opération est réussie, alors le flux est ajouté à la base et sa persistance est assurée.<br>" +
                "Le flux XML retourné contient les informations suivantes :<br>" +
                "- id → numéro d'identifiant attribué au dernier article enregistré<br>" +
                "- status → INSERTED<br>" +
                "En cas d'échec de l'opération, seule la valeur de statut est retournée<br>" +
                "- status → ERROR");
        endpoints.add(insert);

        // Delete article
        Map<String, String> delete = new HashMap<>();
        delete.put("url", "/rss25SB/delete/{id}");
        delete.put("method", "DELETE");
        delete.put("operation", "Suppression de l'article dont l'identifiant est {id}");
        delete.put("return", "Flux XML");
        delete.put("description", "Pour garantir la cohérence, tout flux rss25SB vide (c'est à dire ne contenant aucun article) doit être supprimé<br>" +
                "Si l'opération à réussi, retour des informations suivantes :<br>" +
                "- id → numéro d'identifiant de l'article qui a été retiré<br>" +
                "- status → DELETED<br>" +
                "Si l'opération à échoué<br>" +
                "- status → ERROR");
        endpoints.add(delete);

        // Insert page
        Map<String, String> insertPage = new HashMap<>();
        insertPage.put("url", "/insert");
        insertPage.put("method", "GET");
        insertPage.put("operation", "Affiche la page d'insertion de flux RSS");
        insertPage.put("return", "Format HTML ou XHTML valide");
        insertPage.put("description", "Affiche un formulaire permettant d'insérer un nouveau flux RSS.<br>" +
                "Le formulaire contient un champ de texte pour entrer le XML du flux et des boutons pour envoyer ou effacer le contenu.<br>" +
                "Un exemple de flux XML est affiché pour guider l'utilisateur.");
        endpoints.add(insertPage);

        // Items page
        Map<String, String> itemsPage = new HashMap<>();
        itemsPage.put("url", "/items");
        itemsPage.put("method", "GET");
        itemsPage.put("operation", "Affiche la liste des articles avec options de suppression");
        itemsPage.put("return", "Format HTML ou XHTML valide");
        itemsPage.put("description", "Affiche la liste des articles présents dans la base avec des options pour :<br>" +
                "• Voir le détail d'un article<br>" +
                "• Supprimer un article<br>" +
                "• Rechercher des articles par titre<br>" +
                "La liste est paginée pour faciliter la navigation.");
        endpoints.add(itemsPage);

        model.addAttribute("endpoints", endpoints);
        return "help";
    }
}
