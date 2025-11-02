package lotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoGeneratorTest {

    @Test
    @DisplayName("랜덤 결과를 정렬하여 Lotto를 생성하고 toString으로 확인할 수 있다")
    void 랜덤_로또_생성_및_출력() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                Lotto lotto = LottoGenerator.generate();
                assertThat(lotto.toString()).isEqualTo("[1, 2, 3, 4, 5, 6]");
            },
            List.of(6, 1, 3, 2, 4, 5)
        );
    }

    @Test
    @DisplayName("중복 없는 6개, 1~45 범위 내 숫자를 생성한다")
    void 랜덤_로또_비중복_및_범위_검증() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                Lotto lotto = LottoGenerator.generate();
                String out = lotto.toString();
                assertThat(out)
                    .startsWith("[")
                    .endsWith("]")
                    .contains(", ")
                    .matches("\\[(\\d{1,2}(, )?){6}\\]");
            },
            List.of(8, 21, 23, 41, 42, 43)
        );
    }

    @Test
    @DisplayName("연속 호출 시 서로 다른 Lotto 인스턴스가 생성된다")
    void 랜덤_여부_확인() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                Lotto first = LottoGenerator.generate();
                Lotto second = LottoGenerator.generate();
                assertThat(first).isNotSameAs(second);
                assertThat(first.toString()).isNotEqualTo(second.toString());
            },
            List.of(1, 2, 3, 4, 5, 6),
            List.of(7, 8, 9, 10, 11, 12)
        );
    }
}
