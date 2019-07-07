package uk.co.kenfos.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import uk.co.kenfos.domain.Coordinate;
import uk.co.kenfos.domain.NavigationInstruction;
import uk.co.kenfos.domain.Robot;
import uk.co.kenfos.domain.Sea;
import uk.co.kenfos.http.json.NavigationInstructionsDeserializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import static io.vavr.collection.List.of;
import static io.vavr.collection.List.ofAll;

@Log
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanRequest {
    @NotNull private Coordinate areaSize;
    @NotNull private Coordinate startingPosition;
    @NotEmpty private Collection<Coordinate> oilPatches;
    @NotEmpty private Collection<NavigationInstruction> navigationInstructions;

    public Try<CleanResponse> execute() {
        var sea = new Sea(areaSize, oilPatches);
        var squaresToClean = ofAll(squaresToClean());
        return Try.of(() -> cleanSea(sea, squaresToClean));
    }

    private CleanResponse cleanSea(Sea sea, List<Coordinate> squaresToClean) {
        validateSquaresToClean(ofAll(squaresToClean()));
        var cleanedSea = squaresToClean.foldLeft(sea, Sea::clean);
        return new CleanResponse(squaresToClean.last(), numberOfSquaresCleaned(cleanedSea));
    }

    private void validateSquaresToClean(List<Coordinate> squaresToClean) {
        if (!anyCoordinateOutOfRange(squaresToClean).isEmpty()) {
            var errorMessage = "Coordinate out of range";
            log.warning(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Option<Coordinate> anyCoordinateOutOfRange(List<Coordinate> squaresToClean) {
        return squaresToClean
            .find(coordinate -> coordinate.getX() >= areaSize.getX() || coordinate.getY() >= areaSize.getY());
    }

    private List<Coordinate> squaresToClean() {
        var robot = new Robot(startingPosition);
        return ofAll(navigationInstructions)
            .foldLeft(of(robot), (robots, instruction) -> robots.append(robots.last().move(instruction)))
            .map(Robot::getCoordinate);
    }

    private int numberOfSquaresCleaned(Sea cleanedSea) {
        return oilPatches.size() - cleanedSea.oilPatches().size();
    }

    @JsonDeserialize(using = NavigationInstructionsDeserializer.class)
    public void setNavigationInstructions(Collection<NavigationInstruction> navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
