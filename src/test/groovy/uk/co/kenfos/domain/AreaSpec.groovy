package uk.co.kenfos.domain

import spock.lang.Specification
import spock.lang.Unroll

class AreaSpec extends Specification {

    @Unroll
    void 'returns if the area contains a coordinate'() {
        given:
        def area = new Area(3, 3)

        expect:
        area.contains(coordinate) == isInsideArea

        where:
        coordinate           | isInsideArea
        new Coordinate(1, 1) | true
        new Coordinate(1, 2) | true
        new Coordinate(2, 1) | true
        new Coordinate(1, 3) | false
        new Coordinate(3, 3) | false
    }
}
