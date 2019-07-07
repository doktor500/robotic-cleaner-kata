package uk.co.kenfos.http.json;

import uk.co.kenfos.domain.Area;

public class AreaDeserializer extends TwoDimesionsItemDeserializer<Area> {
    @Override
    Area newInstance(Integer x, Integer y) {
        return new Area(x, y);
    }
}