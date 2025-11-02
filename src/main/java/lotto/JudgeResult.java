package lotto;

import java.util.EnumMap;
import java.util.Map;

public class JudgeResult {
    private final Map<Rank, Integer> counts;
    private final long totalPrize;

    public JudgeResult(Map<Rank, Integer> counts, long totalPrize) {
        this.counts = new EnumMap<>(counts);
        this.totalPrize = totalPrize;
    }

    public Map<Rank, Integer> counts() {
        return counts;
    }

    public long totalPrize() {
        return totalPrize;
    }
}
