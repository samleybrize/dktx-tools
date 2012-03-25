package com.numericalactivity.dktxtools.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

public class GZipUtilsTest {

    protected static final String FILE_UNCOMPRESSED     = "./testRes/gzipUtilsTest.txt";
    protected static final String FILE_UNCOMPRESSED_GEN = "./testRes/gen/gzipUtilsTest.txt";
    protected static final String FILE_COMPRESSED       = "./testRes/gzipUtilsTest.gz";
    protected static final String FILE_COMPRESSED_GEN   = "./testRes/gen/gzipUtilsTest.gz";

    @Test
    public void testEncodeOutputStreamInputStream() throws ArrayComparisonFailure, NoSuchAlgorithmException, IOException {
        GZipUtils.encode(new FileOutputStream(FILE_COMPRESSED_GEN), new FileInputStream(FILE_UNCOMPRESSED));
        assertArrayEquals("Invalid checksum", FileUtils.getChecksum(FILE_COMPRESSED), FileUtils.getChecksum(FILE_COMPRESSED_GEN));
    }

    @Test
    public void testEncodeFileFile() throws ArrayComparisonFailure, NoSuchAlgorithmException, IOException {
        GZipUtils.encode(new File(FILE_COMPRESSED_GEN), new File(FILE_UNCOMPRESSED));
        assertArrayEquals("Invalid checksum", FileUtils.getChecksum(FILE_COMPRESSED), FileUtils.getChecksum(FILE_COMPRESSED_GEN));
    }

    @Test
    public void testDecodeOutputStreamInputStream() throws ArrayComparisonFailure, NoSuchAlgorithmException, IOException {
        GZipUtils.decode(new FileOutputStream(FILE_UNCOMPRESSED_GEN), new FileInputStream(FILE_COMPRESSED));
        assertArrayEquals("Invalid checksum", FileUtils.getChecksum(FILE_UNCOMPRESSED), FileUtils.getChecksum(FILE_UNCOMPRESSED_GEN));
    }

    @Test
    public void testDecodeFileFile() throws FileNotFoundException, IOException, ArrayComparisonFailure, NoSuchAlgorithmException {
        GZipUtils.decode(new File(FILE_UNCOMPRESSED_GEN), new File(FILE_COMPRESSED));
        assertArrayEquals("Invalid checksum", FileUtils.getChecksum(FILE_UNCOMPRESSED), FileUtils.getChecksum(FILE_UNCOMPRESSED_GEN));
    }

}
