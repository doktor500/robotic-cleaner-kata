package uk.co.kenfos;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.co.kenfos.http.CleanRequest;
import uk.co.kenfos.http.CleanResponse;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.OK;

@Log
@RestController
@RequestMapping(value="/robot")
public class RobotControler {
    @PostMapping("/clean")
    public Mono<ResponseEntity<CleanResponse>> clean(@RequestBody CleanRequest request) {
        log.info(request.toString());
        return Mono.just(new ResponseEntity<>(new CleanResponse(asList(1, 3), 1), OK));
    }
}
