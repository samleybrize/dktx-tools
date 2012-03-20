package com.numericalactivity.dktxtools.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FlagsUtilsTest {

    @Test
    public void testGetFlags() {
        int flagsPacked = 0x1 | 0x4 | 0x1000;
        int[] flags     = FlagsUtils.getFlags(flagsPacked);

        assertEquals("Wrong number of flags", 3, flags.length);
        assertEquals("Wrong flag", 0x1, flags[0]);
        assertEquals("Wrong flag", 0x4, flags[1]);
        assertEquals("Wrong flag", 0x1000, flags[2]);
    }

    @Test
    public void testToHexString() {
        int flagsPacked = 0x1 | 0x4 | 0x1000;
        int[] flags     = FlagsUtils.getFlags(flagsPacked);
        assertEquals("0x1, 0x4, 0x1000", FlagsUtils.toHexString(flags));
    }

}
