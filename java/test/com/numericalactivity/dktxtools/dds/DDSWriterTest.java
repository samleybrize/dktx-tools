package com.numericalactivity.dktxtools.dds;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class DDSWriterTest extends WriterTestAbstract {

    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP               = "./testRes/dds/uncompressed_no_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP_GEN           = "./testRes/gen/uncompressed_no_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_MIPMAP                  = "./testRes/dds/uncompressed_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_MIPMAP_GEN              = "./testRes/gen/uncompressed_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP       = "./testRes/dds/uncompressed_cubemap_no_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN   = "./testRes/gen/uncompressed_cubemap_no_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP          = "./testRes/dds/uncompressed_cubemap_mipmap.dds";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN      = "./testRes/gen/uncompressed_cubemap_mipmap.dds";
    protected static final String FILE_COMPRESSED_NO_MIPMAP                 = "./testRes/dds/compressed_no_mipmap.dds";
    protected static final String FILE_COMPRESSED_NO_MIPMAP_GEN             = "./testRes/gen/compressed_no_mipmap.dds";
    protected static final String FILE_COMPRESSED_MIPMAP                    = "./testRes/dds/compressed_mipmap.dds";
    protected static final String FILE_COMPRESSED_MIPMAP_GEN                = "./testRes/gen/compressed_mipmap.dds";
    protected static final String FILE_COMPRESSED_CUBEMAP_NO_MIPMAP         = "./testRes/dds/compressed_cubemap_no_mipmap.dds";
    protected static final String FILE_COMPRESSED_CUBEMAP_NO_MIPMAP_GEN     = "./testRes/gen/compressed_cubemap_no_mipmap.dds";
    protected static final String FILE_COMPRESSED_CUBEMAP_MIPMAP            = "./testRes/dds/compressed_cubemap_mipmap.dds";
    protected static final String FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN        = "./testRes/gen/compressed_cubemap_mipmap.dds";

    @Test
    public void testWriteUncompressedNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_NO_MIPMAP_GEN);
            DDSWriter writer        = new DDSWriter(false, false, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
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
            DDSWriter writer        = new DDSWriter(true, false, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _uncompressedTextureBuffer[i]);
            }

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
            DDSWriter writer        = new DDSWriter(false, true, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, 0, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _uncompressedTextureBuffer[0]);
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
            DDSWriter writer        = new DDSWriter(true, true, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, 0, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 1, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 2, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 3, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 4, _uncompressedTextureBuffer[i]);
                writer.getTextureData().set(i, 5, _uncompressedTextureBuffer[i]);
            }

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
            DDSWriter writer        = new DDSWriter(false, false, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            writer.getTextureData().set(0, _compressedTextureBuffer[0]);
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
            DDSWriter writer        = new DDSWriter(true, false, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _compressedTextureBuffer[i]);
            }
            
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
            DDSWriter writer        = new DDSWriter(false, true, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            writer.getTextureData().set(0, 0, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _compressedTextureBuffer[0]);
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
            DDSWriter writer        = new DDSWriter(true, true, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, 0, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 1, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 2, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 3, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 4, _compressedTextureBuffer[i]);
                writer.getTextureData().set(i, 5, _compressedTextureBuffer[i]);
            }
            
            writer.write(out);
            
            assertTrue("md5 checksums not equal", FileUtils.isEqual(FILE_COMPRESSED_CUBEMAP_MIPMAP, FILE_COMPRESSED_CUBEMAP_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteHeaderException() {
        // TODO tester exceptions
    }

    @Test
    public void testWriteHeader10Exception() {
        ByteBuffer buffer           = BufferUtils.getEmptyByteBuffer(DDSHeader.HEADER_LENGTH + DDSHeader.FILE_IDENTIFIER_LENGTH);
        DDSHeader10.Writer writer   = new DDSHeader10.Writer();
        DDSHeader.Writer header     = new DDSHeader.Writer();

        header.setHeader10(writer);
        header.setMipmapCount(1);
        header.setWidth(256);
        header.setHeight(256);
        header.setFlags(DDSHeader.DDSD_CAPS | DDSHeader.DDSD_WIDTH | DDSHeader.DDSD_HEIGHT | DDSHeader.DDSD_PIXELFORMAT | DDSHeader.DDSD_LINEARSIZE);
        header.setCaps(DDSHeader.DDSCAPS_TEXTURE);
        header.setPixelFormatFourCC(DDSFourCC.FOURCC_DX10);
        header.setPixelFormatFlags(DDSHeader.DDPF_FOURCC);

        // vérification du nombre d'éléments
        try {
            writer.setArraySize(1);
            header.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Texture arrays are not supported", e.getMessage());
        }

        // vérification du dxgiFormat
        try {
            writer.setArraySize(0);
            header.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Invalid dxgiFormat value 0x", e.getMessage().substring(0, 27));
        }

        // vérification du dxgiFormat
        try {
            writer.setDxgiFormat(DDSFourCC.FOURCC_ATC);
            writer.setResourceDimension(DDSHeader10.DDS_DIMENSION_TEXTURE3D);
            header.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("resourceDimension is set to DDS_DIMENSION_TEXTURE3D but 3D textures are not supported", e.getMessage());
        }
    }

    @Test
    public void testWriteTextureDataException() throws IOException, DDSFormatException {
        DDSHeader.Writer header         = new DDSHeader.Writer();
        DDSTextureData.Writer writer    = new DDSTextureData.Writer(header, 0, 2);
        ByteBuffer buffer               = BufferUtils.getEmptyByteBuffer(10);

        // vérification de la présence de données pour les niveaux mipmaps
        try {
            writer.write(null);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("No data defined for mipmap level 0", e.getMessage());
        }

        // vérification de la présence de données pour les faces
        try {
            writer.set(0, buffer);
            writer.write(null);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("No data defined for face 1", e.getMessage().substring(0, 26));
        }

        // vérification de la cohérence entre le nombre de faces dans les données et les headers
        try {
            writer.set(0, 1, buffer);
            writer.write(null);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("1 faces defined in headers", e.getMessage().substring(0, 26));
        }

        // vérification de la cohérence entre le nombre de niveaux mipmap dans les données et les headers
        try {
            header.setCaps2(DDSHeader.DDSCAPS2_CUBEMAP | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY);
            header.setMipmapCount(5);
            writer.write(null);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("5 mipmap levels defined in headers", e.getMessage().substring(0, 34));
        }
    }
}
