package uk.co.kenfos.http.json.deserializer;

import uk.co.kenfos.domain.Coordinate;

public class CoordinateDeserializer extends TwoDimesionsItemDeserializer<Coordinate> {
    @Override
    Coordinate newInstance(Integer x, Integer y) {
        return new Coordinate(x, y);
    }
}