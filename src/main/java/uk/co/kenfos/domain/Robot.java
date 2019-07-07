package uk.co.kenfos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Robot {
    private Coordinate coordinate;

    public Robot move(NavigationInstruction navigationInstruction) {
        return switch (navigationInstruction) {
            case NORTH -> new Robot(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
            case SOUTH -> new Robot(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
            case WEST  -> new Robot(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
            case EAST  -> new Robot(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
        };
    }
}
