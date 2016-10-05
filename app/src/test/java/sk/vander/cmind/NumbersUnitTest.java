package sk.vander.cmind;

import org.junit.Test;

import sk.vander.cmind.data.NumbersProvider;

import static org.junit.Assert.*;

public class NumbersUnitTest {
    NumbersProvider data = new NumbersProvider();

    @Test
    public void generation() throws Exception {
        for (int i = 0; i < 10; i++) {
            String num = data.number();
            assertTrue(num.length() > 0 && num.length() <= 20);
        }
    }

    @Test
    public void asString() throws Exception {
        assertEquals("one 1 two 2 two 9", data.makeString("12299"));
        assertEquals("seven 4 six 1 seven 0", data.makeString("44444441111110000000"));
        assertEquals("one 8", data.makeString("8"));
    }
}