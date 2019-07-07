package uk.co.kenfos.http.json.deserializer

import spock.lang.Specification
import spock.lang.Unroll
import uk.co.kenfos.domain.Coordinate

import static uk.co.kenfos.utils.JsonUtils.jsonParser

class CoordinateDeserializerSpec extends Specification {

    private CoordinateDeserializer coordinateDeserializer

    void setup() {
        coordinateDeserializer = new CoordinateDeserializer()
    }

    @Unroll
    void 'parses valid json to a coordinate instance'() {
        expect:
        coordinateDeserializer.deserialize(jsonParser(json), null) == coordinate

        where:
        json     | coordinate
        '[1, 2]' | new Coordinate(1, 2)
        '[2, 3]' | new Coordinate(2, 3)
    }

    @Unroll
    void 'raises an exception when it is not possible to create a coordinate instance'() {
        expect:
        Optional.ofNullable(coordinateDeserializer.deserialize(jsonParser(json), null)).empty

        where:
        json << [
            '["a", "b"]',
            '["a"]',
            '[""]',
            '[]'
        ]
    }
}

