package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InputView 테스트")
class InputViewTest extends NsTest {

    private final InputView inputView = new InputView();

    @Test
    @DisplayName("유효: 구입금액 5000원")
    void readPurchaseAmount_valid() {
        run("5000");
        int result = inputView.readPurchaseAmount();
        assertThat(result).isEqualTo(5000);
    }

    @Test
    @DisplayName("예외 후 재시도: 1500(잘못됨) → 5000(정상)")
    void readPurchaseAmount_retry() {
        run("1500", "5000");
        int result = inputView.readPurchaseAmount();
        assertThat(result).isEqualTo(5000);
        assertThat(output()).contains("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
    }

    @Test
    @DisplayName("에러 후 재시도: 빈 입력")
    void readPurchaseAmount_blankRetry() {
        run("   ", "1000");
        int result = inputView.readPurchaseAmount();
        assertThat(result).isEqualTo(1000);
        assertThat(output()).contains("[ERROR] 빈 입력은 허용되지 않습니다.");
    }

    @Test
    @DisplayName("유효: 당첨 번호 입력")
    void readWinningNumbers_valid() {
        run("1,2,3,4,5,6");
        List<Integer> result = inputView.readWinningNumbers();
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("에러 후 재시도: 당첨 번호 중복값 입력")
    void readWinningNumbers_retryOnDuplicate() {
        run("1,2,3,3,4,5", "1,2,3,4,5,6");
        List<Integer> result = inputView.readWinningNumbers();
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6);
        assertThat(output()).contains("[ERROR] 중복 없는 번호를 입력해 주세요.");
    }

    @Test
    @DisplayName("유효: 보너스 번호 입력")
    void readBonusNumber_valid() {
        run("7");
        List<Integer> winning = List.of(1, 2, 3, 4, 5, 6);
        int bonus = inputView.readBonusNumber(winning);
        assertThat(bonus).isEqualTo(7);
    }

    @Test
    @DisplayName("에러 후 재시도: 보너스 번호 와 당첨 번호 중복상")
    void readBonusNumber_duplicateRetry() {
        run("6", "7");
        List<Integer> winning = List.of(1, 2, 3, 4, 5, 6);
        int bonus = inputView.readBonusNumber(winning);
        assertThat(bonus).isEqualTo(7);
        assertThat(output()).contains("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("에러 후 재시도: 보너스 번호 범위 초과")
    void readBonusNumber_outOfRangeRetry() {
        run("50", "7");
        List<Integer> winning = List.of(1, 2, 3, 4, 5, 6);
        int bonus = inputView.readBonusNumber(winning);
        assertThat(bonus).isEqualTo(7);
        assertThat(output()).contains("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @Override
    protected void runMain() {
    }
}
