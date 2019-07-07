package uk.co.kenfos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.co.kenfos.http.CleanRequest;
import uk.co.kenfos.http.CleanResponse;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value="/robot")
public class RobotCleanerControler {
    @PostMapping("/clean")
    public Mono<ResponseEntity<CleanResponse>> clean(@Valid @RequestBody Mono<CleanRequest> request) {
        return request.map(this::cleanSea);
    }

    private ResponseEntity<CleanResponse> cleanSea(CleanRequest request) {
        return request.execute()
            .map(response -> new ResponseEntity<>(response, OK))
            .getOrElse(new ResponseEntity<>(BAD_REQUEST));
    }
}
