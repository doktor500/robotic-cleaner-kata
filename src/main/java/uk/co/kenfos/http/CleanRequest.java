package uk.co.kenfos.http;

import lombok.Data;

import java.util.List;

@Data
public class CleanRequest {
    private List<Integer> areaSize;
    private List<Integer> startingPosition;
    private List<List<Integer>> oilPatches;
    private String navigationInstructions;
}
