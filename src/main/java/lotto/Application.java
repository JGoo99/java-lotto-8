package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        try {
            new Application().run();
        } finally {
            Console.close();
        }
    }

    void run() {
        InputView in = new InputView();
        OutputView out = new OutputView();

        int amount = in.readPurchaseAmount();
        int ticketCount = amount / 1000;

        List<Lotto> tickets = buy(ticketCount);
        out.printTickets(ticketCount, tickets);

        List<Integer> winning = in.readWinningNumbers();
        int bonus = in.readBonusNumber(winning);

        Map<Rank, Integer> counts = new EnumMap<>(Rank.class);
        for (Rank r : Rank.values()) {
            counts.put(r, 0);
        }

        long totalPrize = 0;
        Set<Integer> winSet = new HashSet<>(winning);
        for (Lotto t : tickets) {
            long matched = t.countMatches(winSet);
            boolean bonusMatched = t.contains(bonus);

            Rank rank = Rank.of((int) matched, bonusMatched);
            counts.put(rank, counts.get(rank) + 1);
            totalPrize += rank.prize();
        }

        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.printf("3개 일치 (5,000원) - %d개%n", counts.getOrDefault(Rank.FIFTH, 0));
        System.out.printf("4개 일치 (50,000원) - %d개%n", counts.getOrDefault(Rank.FOURTH, 0));
        System.out.printf("5개 일치 (1,500,000원) - %d개%n", counts.getOrDefault(Rank.THIRD, 0));
        System.out.printf("5개 일치, 보너스 볼 일치 (30,000,000원) - %d개%n", counts.getOrDefault(Rank.SECOND, 0));
        System.out.printf("6개 일치 (2,000,000,000원) - %d개%n", counts.getOrDefault(Rank.FIRST, 0));

        double rate = (totalPrize * 100.0) / amount;
        System.out.printf("총 수익률은 %.1f%%입니다.%n", Math.round(rate * 10) / 10.0);
    }

    private static List<Lotto> buy(int ticketCount) {
        List<Lotto> tickets = new ArrayList<>(ticketCount);
        for (int i = 0; i < ticketCount; i++) {
            tickets.add(LottoGenerator.generate());
        }
        return tickets;
    }
}
