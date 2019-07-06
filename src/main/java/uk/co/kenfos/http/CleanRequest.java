package uk.co.kenfos.http;

import lombok.Data;
import uk.co.kenfos.domain.Coordinate;

import java.util.List;

@Data
public class CleanRequest {
    private List<Integer> areaSize;
    private Coordinate startingPosition;
    private List<Coordinate> oilPatches;
    private String navigationInstructions;
}
