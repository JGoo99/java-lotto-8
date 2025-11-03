package lotto;

import java.util.List;

public class GameRunner {
    private static final int LOTTO_TICKET_PRICE = 1000;
    private final InputView in;
    private final OutputView out;
    private final LottoGame game;

    public GameRunner(InputView in, OutputView out, LottoGame game) {
        this.in = in;
        this.out = out;
        this.game = game;
    }

    public void run() {
        int amount = in.readPurchaseAmount();
        int ticketCount = amount / LOTTO_TICKET_PRICE;

        List<Lotto> tickets = game.generateRandomLotto(ticketCount);
        out.printTickets(ticketCount, tickets);

        List<Integer> winning = in.readWinningNumbers();
        int bonus = in.readBonusNumber(winning);

        JudgeResult result = game.judge(tickets, winning, bonus);
        out.printStats(result, amount);
    }
}
