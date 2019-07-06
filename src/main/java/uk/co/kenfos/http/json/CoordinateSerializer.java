package uk.co.kenfos.http.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.kenfos.domain.Coordinate;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CoordinateSerializer extends StdSerializer<Coordinate> {

    public CoordinateSerializer() {
        super(Coordinate.class);
    }

    @Override
    public void serialize(Coordinate coordinate, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeObject(getCoordinateValues(coordinate));
    }

    private List<Integer> getCoordinateValues(Coordinate value) {
        return Stream.of(value.getX(), value.getY())
            .filter(Objects::nonNull)
            .collect(toList());
    }
}
