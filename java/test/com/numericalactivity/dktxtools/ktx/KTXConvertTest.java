package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.junit.Test;

import com.numericalactivity.dktxtools.dds.DDSFormatException;
import com.numericalactivity.dktxtools.dds.DDSReader;
import com.numericalactivity.dktxtools.dds.DDSWriterTest;
import com.numericalactivity.dktxtools.pvr.PVRFormatException;
import com.numericalactivity.dktxtools.pvr.PVRReader;
import com.numericalactivity.dktxtools.pvr.PVRReaderTest;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class KTXConvertTest {

    public static final String FILE_PVR_COMPRESSED_NO_MIPMAP_CONVERT_KTX                = "./testRes/pvr/compressed_no_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_NO_MIPMAP_CONVERT_KTX_GEN            = "./testRes/gen/compressed_no_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_MIPMAP_CONVERT_KTX                   = "./testRes/pvr/compressed_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_MIPMAP_CONVERT_KTX_GEN               = "./testRes/gen/compressed_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_CUBEMAP_NO_MIPMAP_CONVERT_KTX        = "./testRes/pvr/compressed_cubemap_no_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_CUBEMAP_NO_MIPMAP_CONVERT_KTX_GEN    = "./testRes/gen/compressed_cubemap_no_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_CUBEMAP_MIPMAP_CONVERT_KTX           = "./testRes/pvr/compressed_cubemap_mipmap.pvr.ktx";
    public static final String FILE_PVR_COMPRESSED_CUBEMAP_MIPMAP_CONVERT_KTX_GEN       = "./testRes/gen/compressed_cubemap_mipmap.pvr.ktx";

    @Test
    public void testDDSConvertUncompressedNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertUncompressedMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertUncompressedCubemapNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertUncompressedCubemapMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertCompressedNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_NO_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_NO_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertCompressedMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertCompressedCubemapNoMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testDDSConvertCompressedCubemapMipmap() throws IOException, DDSFormatException, KTXFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP);
        DDSReader reader                    = DDSReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertDDS(reader, new FileOutputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN), metadata);

        assertTrue("md5 checksums not equal", FileUtils.isEqual(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP, KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
    }

    @Test
    public void testPVRConvertCompressedNoMipmap() throws NoSuchAlgorithmException, IOException, KTXFormatException, PVRFormatException {
        FileInputStream in                  = new FileInputStream(PVRReaderTest.FILE_COMPRESSED_NO_MIPMAP);
        PVRReader reader                    = PVRReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertPVR(reader, new FileOutputStream(FILE_PVR_COMPRESSED_NO_MIPMAP_CONVERT_KTX_GEN), metadata);
        
        assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_PVR_COMPRESSED_NO_MIPMAP_CONVERT_KTX, FILE_PVR_COMPRESSED_NO_MIPMAP_CONVERT_KTX_GEN));
    }

    @Test
    public void testPVRConvertCompressedMipmap() throws FileNotFoundException, KTXFormatException, IOException, PVRFormatException, NoSuchAlgorithmException {
        FileInputStream in                  = new FileInputStream(PVRReaderTest.FILE_COMPRESSED_MIPMAP);
        PVRReader reader                    = PVRReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertPVR(reader, new FileOutputStream(FILE_PVR_COMPRESSED_MIPMAP_CONVERT_KTX_GEN), metadata);
        
        assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_PVR_COMPRESSED_MIPMAP_CONVERT_KTX, FILE_PVR_COMPRESSED_MIPMAP_CONVERT_KTX_GEN));
    }

    @Test
    public void testPVRConvertCompressedCubemapNoMipmap() throws FileNotFoundException, KTXFormatException, IOException, NoSuchAlgorithmException, PVRFormatException {
        FileInputStream in                  = new FileInputStream(PVRReaderTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
        PVRReader reader                    = PVRReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertPVR(reader, new FileOutputStream(FILE_PVR_COMPRESSED_CUBEMAP_NO_MIPMAP_CONVERT_KTX_GEN), metadata);
        
        assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_PVR_COMPRESSED_CUBEMAP_NO_MIPMAP_CONVERT_KTX, FILE_PVR_COMPRESSED_CUBEMAP_NO_MIPMAP_CONVERT_KTX_GEN));
    }

    @Test
    public void testPVRConvertCompressedCubemapMipmap() throws NoSuchAlgorithmException, IOException, KTXFormatException, PVRFormatException {
        FileInputStream in                  = new FileInputStream(PVRReaderTest.FILE_COMPRESSED_CUBEMAP_MIPMAP);
        PVRReader reader                    = PVRReader.getNew(in);
        HashMap<String, Object> metadata    = new HashMap<String, Object>(2);
        metadata.put("KTXTest", "testValue");
        metadata.put("KTXTestBytes", KTXWriterTest._ktxMetadataBytes);
        KTXConvert.convertPVR(reader, new FileOutputStream(FILE_PVR_COMPRESSED_CUBEMAP_MIPMAP_CONVERT_KTX_GEN), metadata);
        
        assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_PVR_COMPRESSED_CUBEMAP_MIPMAP_CONVERT_KTX, FILE_PVR_COMPRESSED_CUBEMAP_MIPMAP_CONVERT_KTX_GEN));
    }

}
