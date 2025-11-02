package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        try {
            new Application().run();
        } finally {
            Console.close();
        }
    }

    void run() {
        int amount = readPurchaseAmount();
        int ticketCount = amount / 1000;

        System.out.println();
        System.out.printf("%d개를 구매했습니다.%n", ticketCount);

        List<Lotto> tickets = new ArrayList<>();
        for (int i = 0; i < ticketCount; i++) {
            List<Integer> nums = Randoms.pickUniqueNumbersInRange(1, 45, 6)
                .stream().sorted().collect(Collectors.toList());
            Lotto t = new Lotto(nums);
            tickets.add(t);
            t.printNumbers();
        }

        System.out.println();
        List<Integer> winning = readWinningNumbers();

        System.out.println();
        int bonus = readBonusNumber(winning);
    }

    private int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return retry(() -> {
            String s = Console.readLine();
            int money = parseIntStrict(s);
            if (money <= 0 || money % 1000 != 0) {
                throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위의 양수여야 합니다.");
            }
            return money;
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

    private static int parseIntStrict(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요.");
        }
    }

    @FunctionalInterface
    interface SupplierWithEx<T> {
        T get();
    }

    private List<Integer> readWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        while (true) {
            try {
                String s = Console.readLine();
                List<Integer> nums = parseCommaNumbers(s);
                if (nums.size() != 6) {
                    throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
                }
                validateRangeAndDup(nums);
                return nums.stream().sorted().collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static List<Integer> parseCommaNumbers(String s) {
        String[] parts = s.split(",", -1);
        List<Integer> out = new ArrayList<>();
        for (String p : parts) {
            out.add(parseIntStrict(p));
        }
        return out;
    }

    private static void validateRangeAndDup(List<Integer> nums) {
        Set<Integer> set = new HashSet<>(nums);
        if (set.size() != nums.size()) {
            throw new IllegalArgumentException("[ERROR] 중복 없는 번호를 입력해 주세요.");
        }
        for (int n : nums) {
            validateRange(n);
        }
    }

    private static void validateRange(int n) {
        if (n < 1 || n > 45) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private int readBonusNumber(List<Integer> winning) {
        System.out.println("보너스 번호를 입력해 주세요.");
        while (true) {
            try {
                String s = Console.readLine();
                int n = parseIntStrict(s);
                validateRange(n);
                if (winning.contains(n)) {
                    throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
                }
                return n;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
