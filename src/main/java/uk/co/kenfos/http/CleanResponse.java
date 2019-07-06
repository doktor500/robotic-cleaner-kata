package uk.co.kenfos.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanResponse {
    private List<Integer> finalPosition;
    private Integer oilPatchesCleaned;
}
