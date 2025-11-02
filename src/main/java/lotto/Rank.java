package lotto;

public enum Rank {
    FIRST(2_000_000_000L),
    SECOND(30_000_000L),
    THIRD(1_500_000L),
    FOURTH(50_000L),
    FIFTH(5_000L),
    NONE(0L);

    private final long prize;

    Rank(long p) {
        this.prize = p;
    }

    long prize() {
        return prize;
    }

    static Rank of(int matched, boolean bonus) {
        if (matched == 6) {
            return FIRST;
        }
        if (matched == 5 && bonus) {
            return SECOND;
        }
        if (matched == 5) {
            return THIRD;
        }
        if (matched == 4) {
            return FOURTH;
        }
        if (matched == 3) {
            return FIFTH;
        }
        return NONE;
    }
}
