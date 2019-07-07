package uk.co.kenfos.domain

import spock.lang.Specification
import spock.lang.Unroll

import static uk.co.kenfos.domain.NavigationInstruction.*

class RobotSpec extends Specification {

    @Unroll
    void 'a robot can move to a different position'() {
        given:
        def coordinate = new Coordinate(1, 1)
        def robot = new Robot(coordinate)

        expect:
        robot.move(navigationInstruction) == new Robot(expectedCoordinate)

        where:
        navigationInstruction | expectedCoordinate
        NORTH                 | new Coordinate(1, 2)
        SOUTH                 | new Coordinate(1, 0)
        WEST                  | new Coordinate(0, 1)
        EAST                  | new Coordinate(2, 1)
    }
}
