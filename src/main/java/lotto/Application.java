package lotto;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        try {
            new GameRunner(new InputView(), new OutputView(), new LottoGame()).run();
        } finally {
            Console.close();
        }
    }
}
