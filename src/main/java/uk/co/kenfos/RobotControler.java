package uk.co.kenfos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.co.kenfos.domain.Coordinate;
import uk.co.kenfos.http.CleanRequest;
import uk.co.kenfos.http.CleanResponse;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value="/robot")
public class RobotControler {
    @PostMapping("/clean")
    public Mono<ResponseEntity<CleanResponse>> clean(@RequestBody CleanRequest request) {
        return Mono.just(new ResponseEntity<>(request.execute(), OK));
    }
}
