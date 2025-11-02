package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    @DisplayName("기능 전체 흐름 테스트 (예시 출력과 동일)")
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("8000", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(
                    "8개를 구매했습니다.",
                    "[8, 21, 23, 41, 42, 43]",
                    "[3, 5, 11, 16, 32, 38]",
                    "[7, 11, 16, 35, 36, 44]",
                    "[1, 8, 11, 31, 41, 42]",
                    "[13, 14, 16, 38, 42, 45]",
                    "[7, 11, 30, 40, 42, 43]",
                    "[2, 13, 22, 32, 38, 45]",
                    "[1, 3, 5, 14, 22, 45]",
                    "3개 일치 (5,000원) - 1개",
                    "4개 일치 (50,000원) - 0개",
                    "5개 일치 (1,500,000원) - 0개",
                    "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                    "6개 일치 (2,000,000,000원) - 0개",
                    "총 수익률은 62.5%입니다."
                );
            },
            List.of(8, 21, 23, 41, 42, 43),
            List.of(3, 5, 11, 16, 32, 38),
            List.of(7, 11, 16, 35, 36, 44),
            List.of(1, 8, 11, 31, 41, 42),
            List.of(13, 14, 16, 38, 42, 45),
            List.of(7, 11, 30, 40, 42, 43),
            List.of(2, 13, 22, 32, 38, 45),
            List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    @DisplayName("금액이 숫자가 아니면 예외 메시지 출력")
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Test
    @DisplayName("금액이 1,000원 단위가 아니면 예외가 발생한다")
    void 금액_천단위_예외_후_재입력() {
        assertSimpleTest(
            () -> {
                run("1400", "2000", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(ERROR_MESSAGE);
            }
        );
    }

    @Test
    @DisplayName("금액이 1,000원 단위가 아니면 재입력을 요구하고 구매 개수를 출력한다")
    void 금액_천단위_예외_후_재입력_및_구매_개수_출력() {
        assertSimpleTest(
            () -> {
                run("1400", "2000", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(ERROR_MESSAGE, "2개를 구매했습니다.");
            }
        );
    }

    @Test
    @DisplayName("당첨 번호가 6개가 아니면 재입력을 유도한다")
    void 당첨번호_개수_오류_후_재입력() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("1000", "1,2,3", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(ERROR_MESSAGE);
            },
            List.of(1, 2, 3, 4, 5, 6)
        );
    }

    @Test
    @DisplayName("당첨 번호에 범위(1~45) 밖의 수가 있으면 재입력을 유도한다")
    void 당첨번호_범위_오류_후_재입력() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("1000", "0,2,3,4,5,6", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(ERROR_MESSAGE);
            },
            List.of(10, 11, 12, 13, 14, 15)
        );
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복이면 재입력을 유도한다")
    void 보너스_중복_예외_후_재입력() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("1000", "1,2,3,4,5,6", "6", "7");
                assertThat(output()).contains(ERROR_MESSAGE);
            },
            List.of(21, 22, 23, 24, 25, 26)
        );
    }

    @Test
    @DisplayName("랜덤으로 발행된 로또 번호를 출력한다")
    void 랜덤_로또_출력() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("8000", "1,2,3,4,5,6", "7");
                assertThat(output()).contains(
                    "8개를 구매했습니다.",
                    "[8, 21, 23, 41, 42, 43]",
                    "[3, 5, 11, 16, 32, 38]",
                    "[7, 11, 16, 35, 36, 44]",
                    "[1, 8, 11, 31, 41, 42]",
                    "[13, 14, 16, 38, 42, 45]",
                    "[7, 11, 30, 40, 42, 43]",
                    "[2, 13, 22, 32, 38, 45]",
                    "[1, 3, 5, 14, 22, 45]"
                );
            },
            List.of(8, 21, 23, 41, 42, 43),
            List.of(3, 5, 11, 16, 32, 38),
            List.of(7, 11, 16, 35, 36, 44),
            List.of(1, 8, 11, 31, 41, 42),
            List.of(13, 14, 16, 38, 42, 45),
            List.of(7, 11, 30, 40, 42, 43),
            List.of(2, 13, 22, 32, 38, 45),
            List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
