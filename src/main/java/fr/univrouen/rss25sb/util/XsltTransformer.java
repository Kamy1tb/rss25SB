package fr.univrouen.rss25sb.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Utility class for XML to HTML transformation using XSLT
 */
@Component
public class XsltTransformer {

    private final TransformerFactory transformerFactory;

    /**
     * Constructor that initializes the transformer factory
     */
    public XsltTransformer() {
        this.transformerFactory = TransformerFactory.newInstance();
    }

    /**
     * Transform XML to HTML using the specified XSLT stylesheet
     * @param xml the XML string to transform
     * @param xsltPath the path to the XSLT stylesheet
     * @return the transformed HTML string
     * @throws TransformerException if there is an error during transformation
     * @throws IOException if there is an error reading the XSLT file
     */
    public String transform(String xml, String xsltPath) throws TransformerException, IOException {
        StreamSource xsltSource = new StreamSource(new ClassPathResource(xsltPath).getInputStream());
        Transformer transformer = transformerFactory.newTransformer(xsltSource);
        
        StringReader reader = new StringReader(xml);
        StringWriter writer = new StringWriter();
        
        transformer.transform(new StreamSource(reader), new StreamResult(writer));
        
        return writer.toString();
    }

    /**
     * Transform XML to HTML for item list
     * @param xml the XML string to transform
     * @return the transformed HTML string
     * @throws TransformerException if there is an error during transformation
     * @throws IOException if there is an error reading the XSLT file
     */
    public String transformItemList(String xml) throws TransformerException, IOException {
        return transform(xml, "xslt/item-list.xslt");
    }

    /**
     * Transform XML to HTML for item detail
     * @param xml the XML string to transform
     * @return the transformed HTML string
     * @throws TransformerException if there is an error during transformation
     * @throws IOException if there is an error reading the XSLT file
     */
    public String transformItemDetail(String xml) throws TransformerException, IOException {
        return transform(xml, "xslt/item-detail.xslt");
    }
}