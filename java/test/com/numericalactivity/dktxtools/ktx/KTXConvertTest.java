package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.numericalactivity.dktxtools.dds.DDSFormatException;
import com.numericalactivity.dktxtools.dds.DDSReader;
import com.numericalactivity.dktxtools.dds.DDSWriterTest;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class KTXConvertTest {

    @Test
    public void testConvertUncompressedNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertUncompressedMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertUncompressedCubemapNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertUncompressedCubemapMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertCompressedNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_NO_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_NO_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertCompressedMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertCompressedCubemapNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testConvertCompressedCubemapMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP);
        DDSReader reader            = DDSReader.getNew(in);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN));

        assertTrue("md5 checksums not equal", FileUtils.isEqual(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

}
