package uk.co.kenfos.http.json.deserializer;

import uk.co.kenfos.domain.Area;

public class AreaDeserializer extends TwoDimesionsItemDeserializer<Area> {
    @Override
    Area newInstance(Integer x, Integer y) {
        return new Area(x, y);
    }
}