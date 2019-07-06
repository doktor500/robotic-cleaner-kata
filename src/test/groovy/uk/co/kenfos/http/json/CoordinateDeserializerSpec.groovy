package uk.co.kenfos.http.json

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.kenfos.domain.Coordinate

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
        when:
        coordinateDeserializer.deserialize(jsonParser(json), null)

        then:
        thrown IllegalArgumentException

        where:
        json << [
            '["a", "b"]',
            '["a"]',
            '[""]',
            '[]'
        ]
    }

    private static JsonParser jsonParser(String json) {
        return new JsonFactory(new ObjectMapper()).createParser(json)
    }
}

