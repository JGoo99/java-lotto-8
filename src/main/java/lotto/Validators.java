package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Validators {
    private Validators() {
    }

    public static void validatePurchaseAmount(int money) {
        if (money <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 양수여야 합니다.");
        }
        if (money % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    public static void validateWinningNumbers(List<Integer> nums) {
        if (nums.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
        }
        validateDistinct(nums);
        for (int n : nums) {
            validateRange(n);
        }
    }

    public static void validateBonusDistinct(List<Integer> winning, int bonus) {
        if (winning.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public static void validateDistinct(List<Integer> nums) {
        Set<Integer> set = new HashSet<>(nums);
        if (set.size() != nums.size()) {
            throw new IllegalArgumentException("[ERROR] 중복 없는 번호를 입력해 주세요.");
        }
    }

    public static void validateRange(int n) {
        if (n < 1 || n > 45) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }
}
