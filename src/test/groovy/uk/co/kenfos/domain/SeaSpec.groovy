package uk.co.kenfos.domain

import spock.lang.Specification

import static uk.co.kenfos.domain.Square.OIL
import static uk.co.kenfos.domain.Square.WATER

class SeaSpec extends Specification {

    void 'creates an instance of sea based on the area size and oil patches'() {
        given:
        def oilPatches = [new Coordinate(0, 0), new Coordinate(0, 1)]
        def areaSize = new Coordinate(3, 3)

        expect:
        new Sea(areaSize, oilPatches).squares == [
            [WATER, WATER, WATER],
            [OIL, WATER, WATER],
            [OIL, WATER, WATER],
        ]
    }

    void 'cleans a square in the sea'() {
        given:
        def oilPatches = [new Coordinate(0, 0), new Coordinate(0, 1)]
        def areaSize = new Coordinate(2, 2)
        def sea = new Sea(areaSize, oilPatches)

        expect:
        sea.clean(new Coordinate(0, 0)).squares == [
            [OIL, WATER],
            [WATER, WATER],
        ]
    }

    void 'returns oil patches'() {
        given:
        def oilPatches = [new Coordinate(2, 4)]
        def areaSize = new Coordinate(5, 5)
        def sea = new Sea(areaSize, oilPatches)

        expect:
        sea.oilPatches() == oilPatches
    }
}
