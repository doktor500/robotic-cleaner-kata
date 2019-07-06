package uk.co.kenfos.http.json

import spock.lang.Specification
import spock.lang.Unroll

import static uk.co.kenfos.utils.JsonUtils.jsonParser
import static uk.co.kenfos.domain.NavigationInstruction.*

class NavigationsInstructionDeserializerSpec extends Specification {

    private NavigationInstructionsDeserializer navigationInstructionsDeserializer

    void setup() {
        navigationInstructionsDeserializer = new NavigationInstructionsDeserializer()
    }

    @Unroll
    void 'parses valid json to a navigation instructions instance'() {
        expect:
        navigationInstructionsDeserializer.deserialize(jsonParser(json), null) == navigationInstructions

        where:
        json     | navigationInstructions
        '"NSEW"' | [NORTH, SOUTH, EAST, WEST]
        '"NNEE"' | [NORTH, NORTH, EAST, EAST]
    }

    @Unroll
    void 'raises an exception when it is not possible to create a navigation instructions instance'() {
        when:
        navigationInstructionsDeserializer.deserialize(jsonParser(json), null)

        then:
        thrown IllegalArgumentException

        where:
        json << [
            '"n"',
            '"A"',
            '""'
        ]
    }
}