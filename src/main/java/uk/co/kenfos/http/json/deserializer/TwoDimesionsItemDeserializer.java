package uk.co.kenfos.http.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

import java.io.IOException;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Log
abstract public class TwoDimesionsItemDeserializer<TYPE> extends JsonDeserializer<TYPE> {

    private ObjectMapper objectMapper = new ObjectMapper();

    abstract TYPE newInstance(Integer x, Integer y);

    @Override
    public TYPE deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        var value = readValue(jsonParser);
        return isEmpty(value) ? null : fromJson(value);
    }

    private TYPE fromJson(String text) {
        try {
            var twoDimensionsItem = parseJson(text);
            return newInstance(twoDimensionsItem[0], twoDimensionsItem[1]);
        } catch (Exception exception) {
            log.warning(format("Error occured when trying to parse json '%s'", text));
            return null;
        }
    }

    private String readValue(JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec()
            .readTree(jsonParser)
            .toString();
    }

    private Integer[] parseJson(String text) throws IOException {
        return objectMapper.readValue(text, Integer[].class);
    }
}