package uk.co.kenfos.utils;

import java.util.stream.IntStream;

public class StreamUtils {
    public static IntStream reverseRange(Integer from, Integer to) {
        return IntStream.range(from, to).map(i -> to - i + from - 1);
    }
}
