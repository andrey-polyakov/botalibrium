package botalibrium.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class JsonDateDeserializer  extends JsonDeserializer<Timestamp> {
    static List<SimpleDateFormat> knownPatterns = new LinkedList<>();

    static {
        knownPatterns.add(new SimpleDateFormat("dd MMM, yyyy HH:mm:ss"));
        knownPatterns.add(new SimpleDateFormat("dd MMM, yyyy"));
        knownPatterns.add(new SimpleDateFormat("dd-MMM-yyyy"));
    }

    @Override
    public Timestamp deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        String source = parser.getText();
        Timestamp d = parse(source);
        if (d != null) return d;
        throw new IOException("Unknown date format " + source);
    }

    public static Timestamp parse(String source) {
        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                Timestamp d = new Timestamp(pattern.parse(source).getTime());
                return d;
            } catch (ParseException pe) {
                // Loop on
            }
        }
        return null;
    }
}
