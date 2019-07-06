package uk.co.kenfos.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import uk.co.kenfos.domain.Coordinate;
import uk.co.kenfos.domain.NavigationInstruction;
import uk.co.kenfos.http.json.NavigationInstructionsDeserializer;

import java.util.List;

@Data
public class CleanRequest {
    private Coordinate areaSize;
    private Coordinate startingPosition;
    private List<Coordinate> oilPatches;
    private List<NavigationInstruction> navigationInstructions;

    @JsonDeserialize(using = NavigationInstructionsDeserializer.class)
    public void setNavigationInstructions(List<NavigationInstruction> navigationInstructions) {
        this.navigationInstructions = navigationInstructions;
    }
}
