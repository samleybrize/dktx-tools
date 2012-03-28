package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;

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
            KTXWriter writer        = KTXWriter.getNew(false, false, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_NO_MIPMAP, FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(true, false, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _uncompressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_UNCOMPRESSED_MIPMAP, FILE_UNCOMPRESSED_MIPMAP_GEN));
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedCubemapNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(false, true, _width, _height);
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
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedCubemapMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(true, true, _width, _height);
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
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_NO_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(false, false, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);
            writer.getTextureData().set(0, _compressedTextureBuffer[0]);
            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_NO_MIPMAP, FILE_COMPRESSED_NO_MIPMAP_GEN));
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(true, false, _width, _height);
            writer.setCompressedFormat(TextureFormat.GL_ETC1_RGB8);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _compressedTextureBuffer[i]);
            }

            writer.getMetadata().set("KTXTest", "testValue");
            writer.getMetadata().set("KTXTestBytes", _ktxMetadataBytes);
            writer.write(out);

            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_MIPMAP, FILE_COMPRESSED_MIPMAP_GEN));
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedCubemapNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(false, true, _width, _height);
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
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedCubemapMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN);
            KTXWriter writer        = KTXWriter.getNew(true, true, _width, _height);
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
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteHeaderException() {
        KTXHeader.Writer writer = new KTXHeader.Writer(null);
        ByteBuffer buffer       = BufferUtils.getEmptyByteBuffer(KTXHeader.HEADER_LENGTH);

        // vérification du nombre de niveaux mipmaps en fonction des dimensions
        try {
            buffer.position(0);
            writer.setDimensions(256, 256, 0);
            writer.setNumberOfMipmapLevels(3);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("9 mipmap levels must be set", e.getMessage().substring(0, 27));
        }

        // vérification du nombre de faces
        try {
            buffer.position(0);
            writer.setNumberOfMipmapLevels(1);
            writer.setNumberOfFaces(3);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Either 1 or 6 faces must be defined", e.getMessage().substring(0, 35));
        }

        // vérification du nombre d'éléments
        try {
            buffer.position(0);
            writer.setNumberOfFaces(1);
            writer.setNumberOfArrayElements(1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Texture arrays are not supported", e.getMessage());
        }

        // vérification du type size
        try {
            buffer.position(0);
            writer.setNumberOfArrayElements(0);
            writer.setTypeSize(3);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("glTypeSize must be either 1, 2 or 4", e.getMessage().substring(0, 35));
        }

        // vérification de glType
        try {
            buffer.position(0);
            writer.setTypeSize(1);
            writer.setType(1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("glType must be 0x", e.getMessage().substring(0, 17));
        }

        // vérification du format
        try {
            buffer.position(0);
            writer.setGLFormat(1, 1, 1, TextureFormat.GL_UNSIGNED_BYTE, 1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Invalid glFormat value 0x", e.getMessage().substring(0, 25));
        }

        // on vérifie la cohérence entre glType et glFormat
        try {
            buffer.position(0);
            writer.setType(0);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Both glType and glFormat must equal 0", e.getMessage().substring(0, 37));
        }

        // vérification de internalFormat
        try {
            buffer.position(0);
            writer.setGLFormat(0, 0, 0, 0, 1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Invalid glInternalFormat value (compressed texture)", e.getMessage().substring(0, 51));
        }

        // vérification de internalFormat
        try {
            buffer.position(0);
            writer.setGLFormat(0, 0, TextureFormat.GL_ALPHA, TextureFormat.GL_UNSIGNED_BYTE, 1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Invalid glInternalFormat value (non compressed texture)", e.getMessage().substring(0, 55));
        }

        // vérification de baseInternalFormat
        try {
            buffer.position(0);
            writer.setGLFormat(TextureFormat.GL_ALPHA, 0, TextureFormat.GL_ALPHA, TextureFormat.GL_UNSIGNED_BYTE, 1);
            writer.write(buffer);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("Invalid glBaseInternalFormat value 0x", e.getMessage().substring(0, 37));
        }
    }

    @Test
    public void testWriteTextureDataException() throws IOException, KTXFormatException {
        KTXHeader.Writer header         = new KTXHeader.Writer(null);
        KTXTextureData.Writer writer    = new KTXTextureData.Writer(header, 0, 2);
        ByteBuffer buffer               = BufferUtils.getEmptyByteBuffer(10);

        // vérification de la présence de données pour les niveaux mipmaps
        try {
            writer.write(null);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("No data defined for mipmap level 0", e.getMessage());
        }

        // vérification de la présence de données pour les faces
        try {
            writer.set(0, buffer);
            writer.write(null);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("No data defined for face 1", e.getMessage().substring(0, 26));
        }

        // vérification de la cohérence entre le nombre de faces dans les données et les headers
        try {
            writer.set(0, 1, buffer);
            writer.write(null);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("1 faces defined in headers", e.getMessage().substring(0, 26));
        }

        // vérification de la cohérence entre le nombre de niveaux mipmap dans les données et les headers
        try {
            header.setNumberOfFaces(2);
            header.setNumberOfMipmapLevels(5);
            writer.write(null);
            fail("KTXFormatException expected");
        } catch (KTXFormatException e) {
            assertEquals("5 mipmap levels defined in headers", e.getMessage().substring(0, 34));
        }
    }
}
