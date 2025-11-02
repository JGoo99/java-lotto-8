package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            Lotto lotto = LottoGenerator.generate();
            tickets.add(LottoGenerator.generate());
            System.out.println(lotto);
        }

        System.out.println();
        List<Integer> winning = readWinningNumbers();

        System.out.println();
        int bonus = readBonusNumber(winning);

        Map<Rank, Integer> counts = new EnumMap<>(Rank.class);
        for (Rank r : Rank.values()) {
            counts.put(r, 0);
        }

        long totalPrize = 0;
        Set<Integer> winSet = new HashSet<>(winning);
        for (Lotto t : tickets) {
            long matched = t.countMatches(winSet);
            boolean bonusMatched = t.contains(bonus);

            Rank rank = Rank.of((int) matched, bonusMatched);
            counts.put(rank, counts.get(rank) + 1);
            totalPrize += rank.prize();
        }

        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.printf("3개 일치 (5,000원) - %d개%n", counts.getOrDefault(Rank.FIFTH, 0));
        System.out.printf("4개 일치 (50,000원) - %d개%n", counts.getOrDefault(Rank.FOURTH, 0));
        System.out.printf("5개 일치 (1,500,000원) - %d개%n", counts.getOrDefault(Rank.THIRD, 0));
        System.out.printf("5개 일치, 보너스 볼 일치 (30,000,000원) - %d개%n", counts.getOrDefault(Rank.SECOND, 0));
        System.out.printf("6개 일치 (2,000,000,000원) - %d개%n", counts.getOrDefault(Rank.FIRST, 0));

        double rate = (totalPrize * 100.0) / amount;
        System.out.printf("총 수익률은 %.1f%%입니다.%n", Math.round(rate * 10) / 10.0);
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
