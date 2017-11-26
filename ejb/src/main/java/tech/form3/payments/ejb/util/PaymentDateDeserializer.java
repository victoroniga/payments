package tech.form3.payments.ejb.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Victor Oniga
 */
public class PaymentDateDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {
    private final String[] formats;

    public PaymentDateDeserializer(String[] formats) {
        this.formats = formats;
    }

    public PaymentDateDeserializer() {
        formats = null;
    }

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = parser.getText();
        for (String format : formats) {
            try {
                return new SimpleDateFormat(format).parse(date);
            } catch (ParseException e) {
            }
        }

        throw new RuntimeException("Cannot parse date " + date);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext prov, BeanProperty property) throws JsonMappingException {
        return new PaymentDateDeserializer(property.getAnnotation(PaymentsJsonFormat.class).value());
    }
}
