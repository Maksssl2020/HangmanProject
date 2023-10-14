package hangman.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void testCreatingStringFloatPair() {
        Pair<String, Float> pair = Pair.of("cat", 1.0f);
        assertEquals("(cat, 1.0)", pair.toString());
    }
}