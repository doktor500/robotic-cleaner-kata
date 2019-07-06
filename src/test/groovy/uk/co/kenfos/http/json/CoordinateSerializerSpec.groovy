package uk.co.kenfos.http.json

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll
import uk.co.kenfos.domain.Coordinate

class CoordinateSerializerSpec extends Specification {

    private CoordinateSerializer coordinateSerializer

    void setup() {
        coordinateSerializer = new CoordinateSerializer()
    }

    @Unroll
    void 'converts coordinate to json'() {
        given:
        def outputStream = new ByteArrayOutputStream()
        def jsonGenerator = new JsonFactory(new ObjectMapper()).createGenerator(outputStream)

        when:
        coordinateSerializer.serialize(coordinate, jsonGenerator, null)

        then:
        outputStream.toString() == json

        where:
        coordinate           | json
        new Coordinate(1, 2) | '[1,2]'
        new Coordinate(2, 3) | '[2,3]'
    }

    void 'returns empty list when the coordinate does not contain values'() {
        given:
        def outputStream = new ByteArrayOutputStream()
        def jsonGenerator = new JsonFactory(new ObjectMapper()).createGenerator(outputStream)

        when:
        coordinateSerializer.serialize(new Coordinate(), jsonGenerator, null)

        then:
        outputStream.toString() == '[]'
    }
}