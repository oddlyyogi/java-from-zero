package shelf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FineRuleTest {
    private final FineRule rule = FineRule.capped(25, 500);

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 20, 21, 365})
    void fineNeverPassesTheCap(long days) {
        assertTrue(rule.centsFor(days) <= 500);
    }
}
