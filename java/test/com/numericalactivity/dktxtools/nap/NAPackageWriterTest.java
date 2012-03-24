package com.numericalactivity.dktxtools.nap;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class NAPackageWriterTest {

    protected static final String NAP_OUTPUT_FILE       = "./testRes/gen/testPackage.nap";
    protected static final String NAP_REFERENCE_FILE    = "./testRes/testPackage.nap";
    protected static final String NAP_INNER_TEST_FILE   = "test/test.txt";
    protected static final byte[] NAP_INNER_TEST_BYTES  = {97, 122, 101, 114, 116, 121, 10};

    @Test
    public void testAddEntryStringByteArray() throws IOException, NoSuchAlgorithmException {
        NAPackageWriter writer = new NAPackageWriter(NAP_OUTPUT_FILE);
        writer.addEntry(NAP_INNER_TEST_FILE, NAP_INNER_TEST_BYTES);
        writer.close();

        assertArrayEquals(FileUtils.getChecksum(NAP_REFERENCE_FILE), FileUtils.getChecksum(NAP_OUTPUT_FILE));
    }

    @Test
    public void testAddEntryStringByteBuffer() throws IOException, NoSuchAlgorithmException {
        NAPackageWriter writer = new NAPackageWriter(NAP_OUTPUT_FILE);
        writer.addEntry(NAP_INNER_TEST_FILE, BufferUtils.getByteBuffer(NAP_INNER_TEST_BYTES));
        writer.close();
        
        assertArrayEquals(FileUtils.getChecksum(NAP_REFERENCE_FILE), FileUtils.getChecksum(NAP_OUTPUT_FILE));
    }

    @Test
    public void testAddEntryStringInputStream() throws IOException, NoSuchAlgorithmException, NAPackageException {
        NAPackageWriter writer  = new NAPackageWriter(NAP_OUTPUT_FILE);
        NAPackageReader reader  = new NAPackageReader(NAP_REFERENCE_FILE);
        writer.addEntry(NAP_INNER_TEST_FILE, reader.getInputStream(NAP_INNER_TEST_FILE));
        writer.close();
        
        assertArrayEquals(FileUtils.getChecksum(NAP_REFERENCE_FILE), FileUtils.getChecksum(NAP_OUTPUT_FILE));
    }

}
