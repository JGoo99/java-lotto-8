package lotto;

import java.util.List;

public class OutputView {
    public void printTickets(int ticketCount, List<Lotto> tickets) {
        System.out.printf("%n%d개를 구매했습니다.%n", ticketCount);
        tickets.forEach(System.out::println);
    }
}
