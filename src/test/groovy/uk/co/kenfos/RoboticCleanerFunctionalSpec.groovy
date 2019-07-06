package uk.co.kenfos

import org.junit.Test

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

class RoboticCleanerFunctionalSpec extends FunctionalSpec {

    @Test
    void 'when the request is valid, the robot cleans the sea'() {
        given:
        def validRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NNESEESWNWW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: validRequestBody)

        then:
        response.statusCode == OK.value()
        json(response) == [finalPosition: [1, 3], oilPatchesCleaned : 1]
    }

    @Test
    void 'when the request is invalid, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NNESEESWNWW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }
}
