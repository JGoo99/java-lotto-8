package lotto;

import java.util.ArrayList;
import java.util.List;

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
        List<Integer> out = new ArrayList<>(parts.length);
        for (String p : parts) {
            out.add(parseIntStrict(p));
        }
        return out;
    }
}