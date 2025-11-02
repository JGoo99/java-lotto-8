package lotto;

import java.util.List;
import java.util.stream.Collectors;

public final class Sorts {
    private Sorts() {
    }

    public static List<Integer> sorted(List<Integer> nums) {
        return nums.stream().sorted().collect(Collectors.toList());
    }
}
