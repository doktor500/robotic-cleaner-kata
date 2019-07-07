package uk.co.kenfos

import org.junit.Test
import uk.co.kenfos.base.FunctionalSpec

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

class RoboticCleanerFunctionalSpec extends FunctionalSpec {

    @Test
    void 'the robot cleans one oil patch in the sea'() {
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
    void 'the robot cleans all oil patches in the sea'() {
        given:
        def validRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NESSSW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: validRequestBody)

        then:
        response.statusCode == OK.value()
        json(response) == [finalPosition: [1, 0], oilPatchesCleaned : 3]
    }

    @Test
    void 'when the starting postion is invalid, it returns an error'() {
        given:
        def validRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NESSSW'
        ]
        def invalidRequestBody = validRequestBody << [startingPosition: [1]]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the starting postion is not present, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NESSSW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the area size is not present, it returns an error'() {
        given:
        def invalidRequestBody = [
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NESSSW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the oil patches are not present, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            navigationInstructions: 'NESSSW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the oil patches are empty, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [],
            navigationInstructions: 'NESSSW'
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the navigation instructions are not present, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ]
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }

    @Test
    void 'when the navigation instructions are empty, it returns an error'() {
        given:
        def invalidRequestBody = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: ''
        ]

        when:
        def response = post(resource: '/robot/clean', content: invalidRequestBody)

        then:
        response.statusCode == BAD_REQUEST.value()
    }
}
