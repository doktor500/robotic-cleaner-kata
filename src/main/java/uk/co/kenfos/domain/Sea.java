package uk.co.kenfos.domain;

import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static uk.co.kenfos.domain.Square.OIL;
import static uk.co.kenfos.domain.Square.WATER;
import static uk.co.kenfos.utils.StreamUtils.reverseRange;

@Data
public class Sea {
    private final List<List<Square>> squares;

    public Sea(Area areaSize, Collection<Coordinate> oilPatches) {
        squares = createMatrix(areaSize, oilPatches);
    }

    public Sea clean(Coordinate coordinateToClean) {
        return new Sea(getAreaSize(), oilPatchesMinus(coordinateToClean));
    }

    public List<Coordinate> oilPatches() {
        var rows = squares.size();
        return IntStream.range(0, rows)
            .mapToObj(this::oilPatchesInRow)
            .flatMap(row -> row.filter(Objects::nonNull))
            .map(coordinate -> normalizedCoordinate(rows, coordinate))
            .collect(toList());
    }

    private Area getAreaSize() {
        return new Area(squares.size(), squares.stream().findAny().map(List::size).orElseThrow());
    }

    private List<Coordinate> oilPatchesMinus(Coordinate coordinateToClean) {
        return oilPatches()
            .stream()
            .filter(coordinate -> !coordinate.equals(coordinateToClean))
            .collect(toList());
    }

    private Stream<Coordinate> oilPatchesInRow(Integer row) {
        return IntStream.range(0, squares.get(row).size())
            .mapToObj(column -> (squares.get(row).get(column)) == OIL ? new Coordinate(column, row) : null);
    }

    private List<List<Square>> createMatrix(Area areaSize, Collection<Coordinate> oilPatches) {
        return reverseRange(0, areaSize.getY())
            .mapToObj(row -> createRow(row, areaSize.getX(), oilPatches))
            .collect(toList());
    }

    private List<Square> createRow(Integer row, Integer columns, Collection<Coordinate> oilPatches) {
        return IntStream.range(0, columns)
            .mapToObj(column -> createSquare(column, row, oilPatches))
            .collect(toList());
    }

    private Square createSquare(Integer x, Integer y, Collection<Coordinate> oilPatches) {
        return oilPatches.contains(new Coordinate(x, y)) ? OIL : WATER;
    }

    private Coordinate normalizedCoordinate(Integer rows, Coordinate coordinate) {
        return new Coordinate(coordinate.getX(), rows - coordinate.getY() - 1);
    }
}
