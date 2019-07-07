package uk.co.kenfos.http.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;
import uk.co.kenfos.domain.Coordinate;

import java.io.IOException;

import static java.lang.String.format;

@Log
public class CoordinateDeserializer extends JsonDeserializer<Coordinate> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Coordinate deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        var value = readValue(parser);
        return StringUtils.isEmpty(value) ? null : fromJson(value);
    }

    private Coordinate fromJson(String text) {
        try {
            var coordinate = parseJson(text);
            return new Coordinate(coordinate[0], coordinate[1]);
        } catch (Exception exception) {
            log.warning(format("Error occured when trying to parse json '%s'", text));
            throw new IllegalArgumentException(exception);
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