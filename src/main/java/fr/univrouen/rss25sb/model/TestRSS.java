package fr.univrouen.rss25sb.model;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class TestRSS {

    public String loadFileXML(){
       Resource resource = new DefaultResourceLoader().getResource("classpath:xml/item.xml");

        Charset charset = StandardCharsets.UTF_8;
        try {
            return resource.getContentAsString(charset) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
