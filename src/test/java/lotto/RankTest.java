package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    @DisplayName("6개 일치 시 1등을 반환한다")
    void rank_first() {
        Rank rank = Rank.of(6, false);
        assertThat(rank).isEqualTo(Rank.FIRST);
        assertThat(rank.prize()).isEqualTo(2_000_000_000L);
    }

    @Test
    @DisplayName("5개 + 보너스 일치 시 2등을 반환한다")
    void rank_second() {
        Rank rank = Rank.of(5, true);
        assertThat(rank).isEqualTo(Rank.SECOND);
        assertThat(rank.prize()).isEqualTo(30_000_000L);
    }

    @Test
    @DisplayName("5개만 일치 시 3등을 반환한다")
    void rank_third() {
        Rank rank = Rank.of(5, false);
        assertThat(rank).isEqualTo(Rank.THIRD);
        assertThat(rank.prize()).isEqualTo(1_500_000L);
    }

    @Test
    @DisplayName("4개 일치 시 4등을 반환한다")
    void rank_fourth() {
        Rank rank = Rank.of(4, false);
        assertThat(rank).isEqualTo(Rank.FOURTH);
        assertThat(rank.prize()).isEqualTo(50_000L);
    }

    @Test
    @DisplayName("3개 일치 시 5등을 반환한다")
    void rank_fifth() {
        Rank rank = Rank.of(3, false);
        assertThat(rank).isEqualTo(Rank.FIFTH);
        assertThat(rank.prize()).isEqualTo(5_000L);
    }

    @Test
    @DisplayName("그 외의 조합은 NONE을 반환한다")
    void rank_none() {
        assertThat(Rank.of(2, false)).isEqualTo(Rank.NONE);
        assertThat(Rank.of(1, false)).isEqualTo(Rank.NONE);
        assertThat(Rank.of(0, true)).isEqualTo(Rank.NONE);
        assertThat(Rank.of(0, false).prize()).isEqualTo(0L);
    }
}