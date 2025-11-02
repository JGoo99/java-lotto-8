package lotto;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void printTickets(int ticketCount, List<Lotto> tickets) {
        System.out.printf("%n%d개를 구매했습니다.%n", ticketCount);
        tickets.forEach(System.out::println);
    }

    public void printStats(JudgeResult r, int amount) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");

        Map<Rank, Integer> c = r.counts();
        println("3개 일치 (5,000원) - %d개", c.getOrDefault(Rank.FIFTH, 0));
        println("4개 일치 (50,000원) - %d개", c.getOrDefault(Rank.FOURTH, 0));
        println("5개 일치 (1,500,000원) - %d개", c.getOrDefault(Rank.THIRD, 0));
        println("5개 일치, 보너스 볼 일치 (30,000,000원) - %d개", c.getOrDefault(Rank.SECOND, 0));
        println("6개 일치 (2,000,000,000원) - %d개", c.getOrDefault(Rank.FIRST, 0));

        double rate = (r.totalPrize() * 100.0) / amount;
        System.out.printf("총 수익률은 %.1f%%입니다.%n", Math.round(rate * 10) / 10.0);
    }

    private void println(String fmt, Object v) {
        System.out.printf(fmt + "%n", v);
    }
}
