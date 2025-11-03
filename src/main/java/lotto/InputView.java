package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class InputView {

    public int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return retry(() -> {
            int amount = Parsers.parseIntStrict(Console.readLine());
            Validators.validatePurchaseAmount(amount);
            return amount;
        });
    }

    public List<Integer> readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return retry(() -> {
            List<Integer> winning = Parsers.parseCommaNumbers(Console.readLine());
            Validators.validateWinningNumbers(winning);
            return Sorts.sorted(winning);
        });
    }

    public int readBonusNumber(List<Integer> winning) {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return retry(() -> {
            int bonus = Parsers.parseIntStrict(Console.readLine());
            Validators.validateRange(bonus);
            Validators.validateBonusDistinct(winning, bonus);
            return bonus;
        });
    }

    private <T> T retry(SupplierWithEx<T> sup) {
        while (true) {
            try {
                return sup.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    interface SupplierWithEx<T> {
        T get();
    }
}