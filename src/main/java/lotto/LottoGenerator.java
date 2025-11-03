package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.stream.Collectors;

public class LottoGenerator {
    public static Lotto generate() {
        List<Integer> nums = Randoms.pickUniqueNumbersInRange(
                Validators.MIN_LOTTO_NUMBER, Validators.MAX_LOTTO_NUMBER, Validators.LOTTO_SIZE)
            .stream().sorted().collect(Collectors.toList());
        return new Lotto(nums);
    }
}
