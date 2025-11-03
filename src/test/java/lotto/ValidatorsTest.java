package lotto;

import static lotto.Validators.LOTTO_SIZE;
import static lotto.Validators.MAX_LOTTO_NUMBER;
import static lotto.Validators.MIN_LOTTO_NUMBER;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Validators 테스트")
class ValidatorsTest {

    @Nested
    @DisplayName("validatePurchaseAmount")
    class ValidatePurchaseAmount {

        @ParameterizedTest(name = "유효: {0}원 (1000원 단위 양수)")
        @ValueSource(ints = {1000, 2000, 10_000, 999_000})
        void validAmounts(int money) {
            assertThatCode(() -> Validators.validatePurchaseAmount(money))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest(name = "실패: 비양수 {0}원")
        @ValueSource(ints = {0, -1, -1000, Integer.MIN_VALUE})
        void rejectNonPositive(int money) {
            assertThatThrownBy(() -> Validators.validatePurchaseAmount(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("양수");
        }

        @ParameterizedTest(name = "실패: 1000원 단위 아님 -> {0}원")
        @ValueSource(ints = {1, 999, 1500, 1999, 2_001})
        void rejectNonMultipleOfThousand(int money) {
            assertThatThrownBy(() -> Validators.validatePurchaseAmount(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1,000원 단위");
        }
    }

    @Nested
    @DisplayName("validateRange")
    class ValidateRange {

        @ParameterizedTest(name = "유효 범위: {0}")
        @ValueSource(ints = {MIN_LOTTO_NUMBER, 2, 10, 23, 44, MAX_LOTTO_NUMBER})
        void validRange(int n) {
            assertThatCode(() -> Validators.validateRange(n))
                .doesNotThrowAnyException();
        }

        @ParameterizedTest(name = "실패: 하한 미만 {0}")
        @ValueSource(ints = {0, -1, -100})
        void rejectBelowMin(int n) {
            assertThatThrownBy(() -> Validators.validateRange(n))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1부터 45 사이");
        }

        @ParameterizedTest(name = "실패: 상한 초과 {0}")
        @ValueSource(ints = {46, 100, Integer.MAX_VALUE})
        void rejectAboveMax(int n) {
            assertThatThrownBy(() -> Validators.validateRange(n))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1부터 45 사이");
        }
    }

    @Nested
    @DisplayName("validateDistinct")
    class ValidateDistinct {

        @Test
        @DisplayName("유효: 모두 다른 값")
        void validDistinct() {
            List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
            assertThatCode(() -> Validators.validateDistinct(nums))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("실패: 중복 포함")
        void rejectDuplicates() {
            List<Integer> nums = Arrays.asList(1, 2, 3, 3, 4, 5);
            assertThatThrownBy(() -> Validators.validateDistinct(nums))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복");
        }

        @Test
        @DisplayName("경계: 빈 리스트(헬퍼 특성상 통과, 상위 validate에서 크기 검증됨)")
        void emptyListIsNoopHere() {
            assertThatCode(() -> Validators.validateDistinct(Collections.emptyList()))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("validateWinningNumbers")
    class ValidateWinningNumbers {

        @Test
        @DisplayName("유효: 정확히 6개이고 범위나 중복에 문제가 없는 경우")
        void validWinningNumbers() {
            List<Integer> nums = Arrays.asList(1, 15, 23, 30, 37, 45);
            assertThatCode(() -> Validators.validateWinningNumbers(nums))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("실패: 당첨 번호가 6개 미만인 경우")
        void rejectTooFew() {
            List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
            assertThatThrownBy(() -> Validators.validateWinningNumbers(nums))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.valueOf(LOTTO_SIZE));
        }

        @Test
        @DisplayName("실패: 당첨 번호가 6개 초과된 경우")
        void rejectTooMany() {
            List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            assertThatThrownBy(() -> Validators.validateWinningNumbers(nums))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.valueOf(LOTTO_SIZE));
        }

        @Test
        @DisplayName("실패: 당첨 번호 리스트에 중복이 포함된 경우")
        void rejectDuplicateNumbers() {
            List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 5);
            assertThatThrownBy(() -> Validators.validateWinningNumbers(nums))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복");
        }

        @ParameterizedTest(name = "실패: 당첨번호에 범위 밖 숫자가 포함된 경우")
        @ValueSource(ints = {0, 46, -1, 100})
        void rejectOutOfRangeElement(int bad) {
            List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, bad);
            assertThatThrownBy(() -> Validators.validateWinningNumbers(nums))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1부터 45 사이");
        }

        @Test
        @DisplayName("실패: 당첨 번호 리스트가 null 리스트인 경우")
        void nullList() {
            assertThatThrownBy(() -> Validators.validateWinningNumbers(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
        }

        @Test
        @DisplayName("실패: 로또 번호에 null 포함된 경우")
        void listContainsNullElement() {
            List<Integer> nums = Arrays.asList(1, 2, null, 4, 5, 6);
            assertThatThrownBy(() -> Validators.validateWinningNumbers(nums))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("validateBonusDistinct")
    class ValidateBonusDistinct {

        @Test
        @DisplayName("유효: 보너스가 당첨 번호와 겹치지 않음")
        void validBonus() {
            List<Integer> winning = Arrays.asList(1, 2, 3, 4, 5, 6);
            int bonus = 7;
            assertThatCode(() -> Validators.validateBonusDistinct(winning, bonus))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("실패: 보너스가 당첨 번호와 중복된 경우")
        void rejectBonusDuplicate() {
            List<Integer> winning = Arrays.asList(1, 2, 3, 4, 5, 6);
            int bonus = 6;
            assertThatThrownBy(() -> Validators.validateBonusDistinct(winning, bonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("보너스 번호는 당첨 번호와 중복될 수 없습니다");
        }

        @Test
        @DisplayName("실패: 보너스가 범위 밖 숫자인 경우")
        void bonusRangeShouldBeCheckedSeparately() {
            int invalidBonus = 0;
            assertThatThrownBy(() -> Validators.validateRange(invalidBonus))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("실패: 당첨번호 리스트가 null 인데 보너스랑 중복되는지 검증하는 경우")
        void winningNull() {
            assertThatThrownBy(() -> Validators.validateBonusDistinct(null, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
        }
    }
}
