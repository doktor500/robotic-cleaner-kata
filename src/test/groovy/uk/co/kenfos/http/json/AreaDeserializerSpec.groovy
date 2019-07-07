package uk.co.kenfos.http.json

import spock.lang.Specification
import spock.lang.Unroll
import uk.co.kenfos.domain.Area

import static uk.co.kenfos.utils.JsonUtils.jsonParser

class AreaDeserializerSpec extends Specification {

    private AreaDeserializer areaDeserializer

    void setup() {
        areaDeserializer = new AreaDeserializer()
    }

    @Unroll
    void 'parses valid json to an area instance'() {
        expect:
        areaDeserializer.deserialize(jsonParser(json), null) == area

        where:
        json     | area
        '[1, 2]' | new Area(1, 2)
        '[2, 3]' | new Area(2, 3)
    }

    @Unroll
    void 'raises an exception when it is not possible to create an area instance'() {
        expect:
        Optional.ofNullable(areaDeserializer.deserialize(jsonParser(json), null)).empty

        where:
        json << [
            '["a", "b"]',
            '["a"]',
            '[""]',
            '[]'
        ]
    }
}