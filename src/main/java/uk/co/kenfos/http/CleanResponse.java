package uk.co.kenfos.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.kenfos.domain.Coordinate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanResponse {
    private Coordinate finalPosition;
    private Integer oilPatchesCleaned;
}
