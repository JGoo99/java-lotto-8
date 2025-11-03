package lotto;

import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != Validators.LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        if (numbers.stream().distinct().count() != Validators.LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] 로또 번호에는 중복이 없어야 합니다.");
        }
    }

    public long countMatches(Set<Integer> winning) {
        return numbers.stream().filter(winning::contains).count();
    }

    public boolean contains(int bonus) {
        return numbers.contains(bonus);
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
