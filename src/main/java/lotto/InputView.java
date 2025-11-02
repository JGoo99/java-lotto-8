package lotto;

import static lotto.Parsers.parseCommaNumbers;
import static lotto.Parsers.parseIntStrict;
import static lotto.Validators.validateBonusDistinct;
import static lotto.Validators.validatePurchaseAmount;
import static lotto.Validators.validateRange;
import static lotto.Validators.validateWinningNumbers;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class InputView {

    public int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return retry(() -> {
            int money = parseIntStrict(Console.readLine());
            validatePurchaseAmount(money);
            return money;
        });
    }

    public List<Integer> readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return retry(() -> {
            List<Integer> nums = parseCommaNumbers(Console.readLine());
            validateWinningNumbers(nums);
            return Sorts.sorted(nums);
        });
    }

    public int readBonusNumber(List<Integer> winning) {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return retry(() -> {
            int n = parseIntStrict(Console.readLine());
            validateRange(n);
            validateBonusDistinct(winning, n);
            return n;
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