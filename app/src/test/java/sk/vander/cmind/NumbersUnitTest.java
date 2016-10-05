package sk.vander.cmind;

import org.junit.Test;

import java.util.List;

import sk.vander.cmind.data.NumbersProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumbersUnitTest {
    NumbersProvider data = new NumbersProvider();

    @Test
    public void generation() throws Exception {
        for (int i = 0; i < 10; i++) {
            List<Integer> num = data.number();
            assertTrue(num.size() > 0 && num.size() <= 20);
        }
    }

    @Test
    public void asString() throws Exception {
        assertEquals("one 1 two 2 two 9", data.makeString("12299"));
        assertEquals("seven 4 six 1 seven 0", data.makeString("44444441111110000000"));
        assertEquals("one 8", data.makeString("8"));
    }
}