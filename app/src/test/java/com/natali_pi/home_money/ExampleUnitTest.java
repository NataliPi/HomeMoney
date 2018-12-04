package com.natali_pi.home_money;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        float max = 314;
        String tempMax = ""+(int)max;
        if (tempMax.length() > 2) {
            max = max - (max % (int)Math.pow(10, tempMax.length()-1));
            max += Math.pow(10, tempMax.length() -1);
        }
        assertEquals(400, (int)max);
    }
}