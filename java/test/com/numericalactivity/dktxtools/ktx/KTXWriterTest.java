package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.io.FileOutputStream;

import org.junit.Test;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.FileUtils;

//TODO tester exceptions?
public class KTXWriterTest extends WriterTestAbstract {

    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP               = "./testRes/ktx/uncompressed_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP_GEN           = "./testRes/gen/uncompressed_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_MIPMAP                  = "./testRes/ktx/uncompressed_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_MIPMAP_GEN              = "./testRes/gen/uncompressed_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP       = "./testRes/ktx/uncompressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN   = "./testRes/gen/uncompressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP          = "./testRes/ktx/uncompressed_cubemap_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN      = "./testRes/gen/uncompressed_cubemap_mipmap.ktx";
    protected static final String FILE_COMPRESSED_NO_MIPMAP                 = "./testRes/ktx/compressed_no_mipmap.ktx";
    protected static final String FILE_COMPRESSED_NO_MIPMAP_GEN             = "./testRes/gen/compressed_no_mipmap.ktx";
    protected static final String FILE_COMPRESSED_MIPMAP                    = "./testRes/ktx/compressed_mipmap.ktx";
    protected static final String FILE_COMPRESSED_MIPMAP_GEN                = "./testRes/gen/compressed_mipmap.ktx";
    protected static final String FILE_COMPRESSED_CUBEMAP_NO_MIPMAP         = "./testRes/ktx/compressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN     = "./testRes/gen/compressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_COMPRESSED_CUBEMAP_MIPMAP            = "./testRes/ktx/compressed_cubemap_mipmap.ktx";
    protected static final String FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN        = "./testRes/gen/compressed_cubemap_mipmap.ktx";

    @Test
    public void testWriteUncompressedNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_NO_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(false, false, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_NO_MIPMAP, FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(true, false, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _uncompressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_MIPMAP, FILE_UNCOMPRESSED_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedCubemapNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(false, true, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, 0, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _uncompressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP, FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedCubemapMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(true, true, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, 0, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 1, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 2, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 3, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 4, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 5, _uncompressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_CUBEMAP_MIPMAP, FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_NO_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(false, false, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);
            writer.getTextureData().set(0, _compressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_NO_MIPMAP, FILE_COMPRESSED_NO_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(true, false, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _compressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_MIPMAP, FILE_COMPRESSED_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedCubemapNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(false, true, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);
            writer.getTextureData().set(0, 0, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _compressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_CUBEMAP_NO_MIPMAP, FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedCubemapMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(true, true, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, 0, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 1, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 2, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 3, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 4, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 5, _compressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_CUBEMAP_MIPMAP, FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
