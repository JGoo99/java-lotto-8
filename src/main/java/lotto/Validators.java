package lotto;

import java.util.List;

public final class Validators {
    public static final int MIN_LOTTO_NUMBER = 1;
    public static final int MAX_LOTTO_NUMBER = 45;
    public static final int LOTTO_SIZE = 6;

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
        if (nums == null) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호 리스트가 null일 수 없습니다.");
        }
        if (nums.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
        }
        if (nums.contains(null)) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호에 null이 포함될 수 없습니다.");
        }
        validateDistinct(nums);
        nums.forEach(Validators::validateRange);
    }

    public static void validateBonusDistinct(List<Integer> winning, int bonus) {
        if (winning == null) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호 리스트가 null일 수 없습니다.");
        }
        if (winning.contains(null)) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호에 null이 포함될 수 없습니다.");
        }
        if (winning.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public static void validateDistinct(List<Integer> nums) {
        if (nums == null) {
            throw new IllegalArgumentException("[ERROR] 번호 리스트가 null일 수 없습니다.");
        }
        if (nums.contains(null)) {
            throw new IllegalArgumentException("[ERROR] 번호에 null이 포함될 수 없습니다.");
        }
        if (nums.stream().distinct().count() != nums.size()) {
            throw new IllegalArgumentException("[ERROR] 중복 없는 번호를 입력해 주세요.");
        }
    }

    public static void validateRange(int n) {
        if (n < MIN_LOTTO_NUMBER || n > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }
}
