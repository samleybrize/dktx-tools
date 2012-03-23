package com.numericalactivity.dktxtools.nap;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipException;

import org.junit.Test;

public class NAPackageReaderTest {

    protected static final String NAP_FILE              = "./testRes/testPackage.nap";
    protected static final String NAP_INNER_TEST_FILE   = "test/test.txt";
    protected static final byte[] NAP_INNER_TEST_BYTES  = {97, 122, 101, 114, 116, 121, 10};

    @Test
    public void testGetInputStream() throws ZipException, IOException, NAPackageException {
        NAPackageReader reader      = new NAPackageReader(NAP_FILE);
        InputStream in              = reader.getInputStream(NAP_INNER_TEST_FILE);
        long size                   = reader.getEntrySize(NAP_INNER_TEST_FILE);

        byte[] data                 = new byte[(int) size];
        in.read(data);
        assertArrayEquals(NAP_INNER_TEST_BYTES, data);
    }

    @Test
    public void testGet() throws ZipException, IOException, NAPackageException, NoSuchAlgorithmException {
        NAPackageReader reader      = new NAPackageReader(NAP_FILE);
        ByteBuffer buffer           = reader.get(NAP_INNER_TEST_FILE);
        byte[] data                 = new byte[buffer.capacity()];
        buffer.get(data);

        assertArrayEquals(NAP_INNER_TEST_BYTES, data);
    }

    @Test
    public void testGetEntrySize() throws ZipException, IOException, NAPackageException {
        NAPackageReader reader = new NAPackageReader(NAP_FILE);
        assertEquals(7, reader.getEntrySize(NAP_INNER_TEST_FILE));
    }

}
