package uk.co.kenfos.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.kenfos.domain.Coordinate;
import uk.co.kenfos.domain.NavigationInstruction;
import uk.co.kenfos.domain.Robot;
import uk.co.kenfos.domain.Sea;
import uk.co.kenfos.http.json.NavigationInstructionsDeserializer;

import java.util.List;

import static io.vavr.collection.List.of;
import static io.vavr.collection.List.ofAll;
import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanRequest {
    private Coordinate areaSize;
    private Coordinate startingPosition;
    private List<Coordinate> oilPatches;
    private List<NavigationInstruction> navigationInstructions;

    public Try<CleanResponse> execute() {
        var sea = new Sea(areaSize, oilPatches);
        var squaresToClean = ofAll(squaresToClean());
        return Try.of(() ->cleanSea(sea, squaresToClean));
    }

    private CleanResponse cleanSea(Sea sea, io.vavr.collection.List<Coordinate> squaresToClean) {
        validateSquaresToClean(ofAll(squaresToClean()));
        var cleanedSea = squaresToClean.foldLeft(sea, Sea::clean);
        return new CleanResponse(squaresToClean.last(), numberOfSquaresCleaned(cleanedSea));
    }

    private void validateSquaresToClean(io.vavr.collection.List<Coordinate> squaresToClean) {
        var coordinatesOutOfRange = anyCoordinateOutOfRange(squaresToClean);
        if (!coordinatesOutOfRange.isEmpty()) throw new IllegalArgumentException("Coordinate out of range");
    }

    private Option<Coordinate> anyCoordinateOutOfRange(io.vavr.collection.List<Coordinate> squaresToClean) {
        return squaresToClean
            .find(coordinate -> coordinate.getX() >= areaSize.getX() || coordinate.getY() >= areaSize.getY());
    }

    private List<Coordinate> squaresToClean() {
        var robot = new Robot(startingPosition);
        return ofAll(navigationInstructions)
            .foldLeft(of(robot), (robots, instruction) -> robots.append(robots.last().move(instruction)))
            .map(Robot::getCoordinate)
            .collect(toList());
    }

    private int numberOfSquaresCleaned(Sea cleanedSea) {
        return oilPatches.size() - cleanedSea.oilPatches().size();
    }

    @JsonDeserialize(using = NavigationInstructionsDeserializer.class)
    public void setNavigationInstructions(List<NavigationInstruction> navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
