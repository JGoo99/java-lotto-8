package lotto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Parsers {
    private Parsers() {
    }

    public static int parseIntStrict(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.");
        }
    }

    public static List<Integer> parseCommaNumbers(String s) {
        String[] parts = s.split(",", -1);
        return Arrays.stream(parts)
            .map(Parsers::parseIntStrict)
            .collect(Collectors.toList());
    }
}