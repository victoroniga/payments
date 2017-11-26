package tech.form3.payments.ejb.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Victor Oniga
 */
public class PaymentDateSerializer extends JsonSerializer<Date> implements ContextualSerializer {
    private final String[] formats;

    public PaymentDateSerializer(String[] formats) {
        this.formats = formats;
    }

    public PaymentDateSerializer() {
        formats = null;
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(new SimpleDateFormat(formats[0]).format(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new PaymentDateSerializer(property.getAnnotation(PaymentsJsonFormat.class).value());
    }
}
