package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

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
        LottoGame game = new LottoGame();

        int amount = in.readPurchaseAmount();
        int ticketCount = amount / 1000;

        List<Lotto> tickets = game.buy(ticketCount);
        out.printTickets(ticketCount, tickets);

        List<Integer> winning = in.readWinningNumbers();
        int bonus = in.readBonusNumber(winning);

        JudgeResult result = game.judge(tickets, winning, bonus);
        out.printStats(result, amount);
    }
}
