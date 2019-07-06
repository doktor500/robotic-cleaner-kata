package uk.co.kenfos.http;

import lombok.Data;
import uk.co.kenfos.domain.Coordinate;

import java.util.List;

@Data
public class CleanRequest {
    private Coordinate areaSize;
    private Coordinate startingPosition;
    private List<Coordinate> oilPatches;
    private String navigationInstructions;
}
