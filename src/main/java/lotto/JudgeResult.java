package lotto;

import java.util.EnumMap;
import java.util.Map;

public record JudgeResult(Map<Rank, Integer> counts, long totalPrize) {
    public JudgeResult(Map<Rank, Integer> counts, long totalPrize) {
        this.counts = new EnumMap<>(counts);
        this.totalPrize = totalPrize;
    }
}
