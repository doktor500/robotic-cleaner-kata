package uk.co.kenfos

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import uk.co.kenfos.domain.Coordinate
import uk.co.kenfos.http.CleanResponse

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.web.reactive.function.BodyInserters.fromObject

@WebFluxTest
@RunWith(SpringRunner)
class RoboticCleanerFunctionalSpec {

    @Autowired private WebTestClient webClient

    @Test
    void 'the robot cleans the sea'() {
        given:
        def request = [
            areaSize: [5, 5],
            startingPosition: [1, 2],
            oilPatches: [
                [1, 0],
                [2, 2],
                [2, 3]
            ],
            navigationInstructions: 'NNESEESWNWW'
        ]

        expect:
        webClient.post().uri('/robot/clean')
            .contentType(APPLICATION_JSON)
            .body(fromObject(request))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CleanResponse)
            .isEqualTo(new CleanResponse(new Coordinate(1, 3), 1))
    }
}
