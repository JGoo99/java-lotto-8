package lotto;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 개수가 6개보다 적으면 예외가 발생한다")
    @Test
    void 로또_번호의_개수가_부족하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨번호화 일치하는 개수를 반환한다")
    @Test
    void 당첨번호화_일치하는_개수를_반환한다() {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        // when (6개 일치) // then
        assertThat(lotto.countMatches(Set.of(1, 2, 3, 4, 5, 6))).isEqualTo(6);
        // when (3개 일치) // then
        assertThat(lotto.countMatches(Set.of(1, 2, 3, 40, 41, 42))).isEqualTo(3);
        // when (0개 일치) // then
        assertThat(lotto.countMatches(Set.of(10, 20, 30, 40, 41, 42))).isEqualTo(0);
    }

    @Test
    @DisplayName("보너스 번호 포함 여부를 반환한다")
    void 보너스_번호_포함_여부를_반환한다() {
        // given
        Lotto lotto = new Lotto(List.of(10, 20, 30, 40, 41, 42));
        // when // then
        assertThat(lotto.contains(10)).isTrue();
        assertThat(lotto.contains(11)).isFalse();
    }
}
