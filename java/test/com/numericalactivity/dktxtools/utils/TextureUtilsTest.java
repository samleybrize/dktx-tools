package com.numericalactivity.dktxtools.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextureUtilsTest {

    @Test
    public void testGetNumberOfMipmaps() {
        assertEquals(9, TextureUtils.getNumberOfMipmaps(256, 128));
        assertEquals(10, TextureUtils.getNumberOfMipmaps(256, 512));
    }

    @Test
    public void testGetDimensionForMipmapLevel() {
        assertEquals(64, TextureUtils.getDimensionForMipmapLevel(3, 512));
        assertEquals(8, TextureUtils.getDimensionForMipmapLevel(7, 1024));
    }

}
