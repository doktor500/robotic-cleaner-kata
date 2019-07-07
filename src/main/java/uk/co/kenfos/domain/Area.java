package uk.co.kenfos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private Integer x;
    private Integer y;

    public boolean contains(Coordinate coordinate) {
        return coordinate.getX() < x && coordinate.getY() < y;
    }
}
