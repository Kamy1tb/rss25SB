package fr.univrouen.rss25sb.controllers;

import fr.univrouen.rss25sb.model.Item;
import fr.univrouen.rss25sb.model.TestRSS;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @RequestMapping(value = "/testpost", method = RequestMethod.POST,
            consumes = "application/xml")
    public String postTest(@RequestBody String flux) {
        return ("<result><response>Message reçu : </response>"
                + flux + "</result>");
    }

    @PostMapping(value = "/postrss", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String postRSS() {
        TestRSS rss = new TestRSS();
        return rss.loadFileXML();
    }

    @RequestMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Item getXML() {
        Item it = new Item("12345678","Test item","2022-05-01T11:22:33");
        return it;
    }

}