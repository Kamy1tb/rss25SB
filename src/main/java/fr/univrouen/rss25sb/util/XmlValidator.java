package fr.univrouen.rss25sb.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

/**
 * Utility class for XML validation against XSD schema
 */
@Component
public class XmlValidator {

    private final Validator validator;

    /**
     * Constructor that initializes the validator with the XSD schema
     * @throws SAXException if there is an error parsing the schema
     * @throws IOException if there is an error reading the schema file
     */
    public XmlValidator() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new ClassPathResource("schemas/rss25.tp1.xsd").getInputStream());
        Schema schema = factory.newSchema(schemaFile);
        validator = schema.newValidator();
        
        // Set secure processing features
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    }

    /**
     * Validate XML against the XSD schema
     * @param xml the XML string to validate
     * @return validation result with error message if validation fails
     */
    public ValidationResult validate(String xml) {
        try {
            validator.validate(new StreamSource(new StringReader(xml)));
            return new ValidationResult(true, null);
        } catch (SAXException e) {
            return new ValidationResult(false, e.getMessage());
        } catch (IOException e) {
            return new ValidationResult(false, "Error reading XML: " + e.getMessage());
        }
    }

    /**
     * Class to hold validation result
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;

        public ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}