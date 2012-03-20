package com.numericalactivity.dktxtools.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

public class FileUtilsTest {

    protected static final String CHECKSUM_TEST_FILE    = "./testRes/checksumTestFile";
    protected static final byte[] _checksumByteArray    = {108, 18, -57, -54, 63, -82, 55, -42, -99, 16, 10, 114, 25, 44, -81, -94};
    protected static final byte[] _byteArray            = {4, 5, 12, 14};

    @Test
    public void testMd5ChecksumString() throws ArrayComparisonFailure, NoSuchAlgorithmException, IOException {
        assertArrayEquals(_checksumByteArray, FileUtils.md5Checksum(CHECKSUM_TEST_FILE));
    }

    @Test
    public void testMd5ChecksumFile() throws ArrayComparisonFailure, NoSuchAlgorithmException, IOException {
        assertArrayEquals(_checksumByteArray, FileUtils.md5Checksum(new File(CHECKSUM_TEST_FILE)));
    }

    @Test
    public void testMd5ChecksumByteBuffer() throws ArrayComparisonFailure, NoSuchAlgorithmException {
        assertArrayEquals(_checksumByteArray, FileUtils.md5Checksum(BufferUtils.getByteBuffer(_byteArray)));
    }

    @Test
    public void testMd5ChecksumByteArray() throws ArrayComparisonFailure, NoSuchAlgorithmException {
        assertArrayEquals(_checksumByteArray, FileUtils.md5Checksum(_byteArray));
    }

    @Test
    public void testIsEqualStringString() throws NoSuchAlgorithmException, IOException {
        assertTrue(FileUtils.isEqual(CHECKSUM_TEST_FILE, CHECKSUM_TEST_FILE));
    }

    @Test
    public void testIsEqualFileFile() throws NoSuchAlgorithmException, IOException {
        assertTrue(FileUtils.isEqual(new File(CHECKSUM_TEST_FILE), new File(CHECKSUM_TEST_FILE)));
    }

    @Test
    public void testIsEqualByteBufferByteBuffer() throws NoSuchAlgorithmException, IOException {
        assertTrue(FileUtils.isEqual(BufferUtils.getByteBuffer(_byteArray), BufferUtils.getByteBuffer(_byteArray)));
    }

    @Test
    public void testIsEqualByteArrayByteArray() throws NoSuchAlgorithmException, IOException {
        assertTrue(FileUtils.isEqual(_byteArray, _byteArray));
    }

    @Test
    public void testIsEqualByteBufferByteArray() throws NoSuchAlgorithmException, IOException {
        assertTrue(FileUtils.isEqual(BufferUtils.getByteBuffer(_byteArray), _byteArray));
    }

}
