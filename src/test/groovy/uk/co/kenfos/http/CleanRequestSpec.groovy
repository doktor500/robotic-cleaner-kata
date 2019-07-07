package uk.co.kenfos.http

import spock.lang.Specification
import spock.lang.Unroll
import uk.co.kenfos.domain.Area
import uk.co.kenfos.domain.Coordinate

import static uk.co.kenfos.domain.NavigationInstruction.NORTH

class CleanRequestSpec extends Specification {

    @Unroll
    void 'executes clean request'() {
        given:
        def areaSize = new Area(2, 2)
        def oilPatches = [new Coordinate(0, 0), new Coordinate(0, 1)]
        def request = CleanRequest.builder()
            .areaSize(areaSize)
            .startingPosition(startingPosition)
            .oilPatches(oilPatches)
            .navigationInstructions(navigationInstructions)
            .build()

        expect:
        request.execute().get() == new CleanResponse(finalPosition, coordinatesCleaned)

        where:
        startingPosition     | navigationInstructions | finalPosition        | coordinatesCleaned
        new Coordinate(1, 1) | []                     | new Coordinate(1, 1) | 0
        new Coordinate(0, 0) | []                     | new Coordinate(0, 0) | 1
        new Coordinate(0, 0) | [NORTH]                | new Coordinate(0, 1) | 2
    }

    void 'fails to execute a clean request when the navigation instructions are invalid'() {
        given:
        def areaSize = new Area(2, 2)
        def oilPatches = [new Coordinate(1, 1)]
        def startingPosition = new Coordinate(0, 0)
        def navigationInstructions = [NORTH, NORTH]
        def request = CleanRequest.builder()
            .areaSize(areaSize)
            .startingPosition(startingPosition)
            .oilPatches(oilPatches)
            .navigationInstructions(navigationInstructions)
            .build()

        expect:
        request.execute().failure
    }
}
