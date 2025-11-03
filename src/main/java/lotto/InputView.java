package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class InputView {

    public int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return retry(() -> {
            final String raw = readTrimmedLineOrThrow();
            final int amount = Parsers.parseIntStrict(raw);
            Validators.validatePurchaseAmount(amount);
            return amount;
        });
    }

    public List<Integer> readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return retry(() -> {
            final String raw = readTrimmedLineOrThrow();
            final List<Integer> winning = Parsers.parseCommaNumbers(raw);
            Validators.validateWinningNumbers(winning);
            return Sorts.sorted(winning);
        });
    }

    public int readBonusNumber(List<Integer> winning) {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return retry(() -> {
            final String raw = readTrimmedLineOrThrow();
            final int bonus = Parsers.parseIntStrict(raw);
            Validators.validateRange(bonus);
            Validators.validateBonusDistinct(winning, bonus);
            return bonus;
        });
    }

    private String readTrimmedLineOrThrow() {
        String line = Console.readLine();
        if (line == null) {
            throw new IllegalArgumentException("[ERROR] 입력이 종료되었습니다. 다시 시도해 주세요.");
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 입력은 허용되지 않습니다.");
        }
        return trimmed;
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
