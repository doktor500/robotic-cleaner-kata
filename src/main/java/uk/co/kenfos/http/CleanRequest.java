package uk.co.kenfos.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    public CleanResponse execute() {
        var sea = new Sea(areaSize, oilPatches);
        var squaresToClean = ofAll(squaresToClean());
        validateSquaresToClean(squaresToClean.asJava());
        return cleanSea(sea, squaresToClean);
    }
    
    private void validateSquaresToClean(List<Coordinate> squaresToClean) {
        var coordinateOutOfRange = squaresToClean.stream()
            .filter(coordinate -> coordinate.getX() >= areaSize.getX() || coordinate.getY() >= areaSize.getY())
            .findAny();
        if (coordinateOutOfRange.isPresent()) throw new IllegalArgumentException("Coordinate out of range");
    }

    private CleanResponse cleanSea(Sea sea, io.vavr.collection.List<Coordinate> squaresToClean) {
        var cleanedSea = squaresToClean.foldLeft(sea, Sea::clean);
        return new CleanResponse(squaresToClean.last(), numberOfSquaresCleaned(cleanedSea));
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
