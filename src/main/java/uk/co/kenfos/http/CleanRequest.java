package uk.co.kenfos.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import uk.co.kenfos.domain.*;
import uk.co.kenfos.http.json.deserializer.NavigationInstructionsDeserializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static io.vavr.collection.List.of;
import static io.vavr.collection.List.ofAll;
import static java.lang.Math.toIntExact;

@Log
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleanRequest {
    @NotNull private Area areaSize;
    @NotNull private Coordinate startingPosition;
    @NotEmpty private Collection<Coordinate> oilPatches;
    @NotEmpty private Collection<NavigationInstruction> navigationInstructions;

    public Try<CleanResponse> execute() {
        var sea = new Sea(areaSize, oilPatches);
        var squaresToClean = ofAll(squaresToClean());
        return cleanSea(sea, squaresToClean);
    }

    private Try<CleanResponse> cleanSea(Sea sea, List<Coordinate> squaresToClean) {
        return validSquaresToClean(ofAll(squaresToClean()))
            .map(coordinates -> coordinates.foldLeft(sea, Sea::clean))
            .map(cleanedSea -> new CleanResponse(squaresToClean.last(), numberOfSquaresCleaned(cleanedSea)));
    }

    private Try<List<Coordinate>> validSquaresToClean(List<Coordinate> squaresToClean) {
        return coordinateOutOfRange(squaresToClean).isEmpty() ? Try.success(squaresToClean) : handleOutOfRangeError();
    }

    private Try<List<Coordinate>> handleOutOfRangeError() {
        var errorMessage = "Coordinate out of range";
        log.warning(errorMessage);
        return Try.failure(new IllegalArgumentException(errorMessage));
    }

    private Option<Coordinate> coordinateOutOfRange(List<Coordinate> squaresToClean) {
        return squaresToClean.find(coordinate -> !areaSize.contains(coordinate));
    }

    private List<Coordinate> squaresToClean() {
        var robot = new Robot(startingPosition);
        return ofAll(navigationInstructions)
            .foldLeft(of(robot), (robots, instruction) -> robots.append(robots.last().move(instruction)))
            .map(Robot::getCoordinate);
    }

    private Integer numberOfSquaresCleaned(Sea cleanedSea) {
        return toIntExact(oilPatches.stream().distinct().count() - cleanedSea.oilPatches().stream().distinct().count());
    }

    @JsonDeserialize(using = NavigationInstructionsDeserializer.class)
    public void setNavigationInstructions(Collection<NavigationInstruction> navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
