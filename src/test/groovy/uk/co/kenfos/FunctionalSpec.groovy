package uk.co.kenfos

import com.jayway.restassured.specification.RequestSpecification
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static com.jayway.restassured.http.ContentType.JSON
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@RunWith(SpringRunner)
@ActiveProfiles('test-default')
@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class FunctionalSpec extends Specification {

    @Value('${server.url}') private String serverURL
    @Value('${server.port}') private Integer serverPort

    protected post(Map args) {
        requestTo(args.resource).body(args.content).post()
    }

    protected json(response) {
        response.jsonPath().get()
    }

    private RequestSpecification requestTo(String path) {
        given().accept(JSON).contentType(JSON).baseUri(serverURL).port(serverPort).basePath(path)
    }
}