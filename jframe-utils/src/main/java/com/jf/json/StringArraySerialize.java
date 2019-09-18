package com.jf.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * Description: @JsonSerialize(using = StringArraySerialize.class)
 * User: xujunfei
 * Date: 2018-11-24
 * Time: 21:49
 */
public class StringArraySerialize extends JsonSerializer<Integer[]> {

    @Override
    public void serialize(Integer[] value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if (value != null) {
            gen.writeStartArray();

            for (int i = 0; i < value.length; i++) {
                gen.writeString(String.valueOf(value[i]));
            }

            gen.writeEndArray();
        }
    }
}
