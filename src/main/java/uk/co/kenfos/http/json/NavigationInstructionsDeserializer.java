package uk.co.kenfos.http.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import uk.co.kenfos.domain.NavigationInstruction;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Log
public class NavigationInstructionsDeserializer extends JsonDeserializer<List<NavigationInstruction>> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<NavigationInstruction> deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        var value = readValue(parser);
        return isEmpty(value) ? emptyList() : fromJson(value);
    }

    private List<NavigationInstruction> fromJson(String text) {
        try {
            return parseJson(text);
        } catch (Exception exception) {
            log.warning(format("Error occured when trying to parse json '%s'", text));
            return emptyList();
        }
    }

    private String readValue(JsonParser jsonParser) throws IOException {
        return jsonParser.getCodec()
            .readTree(jsonParser)
            .toString();
    }

    private List<NavigationInstruction> parseJson(String text) throws IOException {
        var instructions = objectMapper.readValue(text, String.class);
        return Arrays.stream(instructions.split(""))
            .map(this::toNavigationInstruction)
            .collect(toList());
    }

    private NavigationInstruction toNavigationInstruction(String navigationInstruction) {
        return stream(NavigationInstruction.values())
            .filter(instruction -> navigationInstruction.equals(toInstructionSymbol(instruction)))
            .findAny()
            .orElseThrow();
    }

    private String toInstructionSymbol(NavigationInstruction instruction) {
        return Character.toString(instruction.name().charAt(0)).toUpperCase();
    }
}
