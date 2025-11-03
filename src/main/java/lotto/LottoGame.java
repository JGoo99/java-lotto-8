package lotto;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LottoGame {

    public List<Lotto> generateRandomLotto(final int ticketCount) {
        List<Lotto> tickets = new ArrayList<>(ticketCount);
        for (int i = 0; i < ticketCount; i++) {
            tickets.add(LottoGenerator.generate());
        }
        return tickets;
    }

    public JudgeResult judge(List<Lotto> tickets, List<Integer> winning, final int bonus) {
        Map<Rank, Integer> counts = initCounts();
        Set<Integer> winSet = new HashSet<>(winning);

        long totalPrize = 0;
        for (Lotto t : tickets) {
            int matched = (int) t.countMatches(winSet);
            boolean bonusMatched = t.contains(bonus);
            Rank rank = Rank.of(matched, bonusMatched);
            counts.put(rank, counts.get(rank) + 1);
            totalPrize += rank.prize();
        }
        return new JudgeResult(counts, totalPrize);
    }

    private Map<Rank, Integer> initCounts() {
        Map<Rank, Integer> rankCount = new EnumMap<>(Rank.class);
        for (Rank r : Rank.values()) {
            rankCount.put(r, 0);
        }
        return rankCount;
    }
}
