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
            DDSWriter writer        = DDSWriter.getNew(false, false, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
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
            DDSWriter writer        = DDSWriter.getNew(true, false, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _uncompressedTextureBuffer[i]);
            }

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
            DDSWriter writer        = DDSWriter.getNew(false, true, _width, _height);
            writer.setUncompressedPixelFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, 0, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _uncompressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _uncompressedTextureBuffer[0]);
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
            DDSWriter writer        = DDSWriter.getNew(true, true, _width, _height);
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
            DDSWriter writer        = DDSWriter.getNew(false, false, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            writer.getTextureData().set(0, _compressedTextureBuffer[0]);
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
            DDSWriter writer        = DDSWriter.getNew(true, false, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                writer.getTextureData().set(i, _compressedTextureBuffer[i]);
            }
            
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
            DDSWriter writer        = DDSWriter.getNew(false, true, _width, _height);
            writer.setCompressedPixelFormat(DDSFourCC.FOURCC_ETC1);
            writer.getTextureData().set(0, 0, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 1, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 2, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 3, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 4, _compressedTextureBuffer[0]);
            writer.getTextureData().set(0, 5, _compressedTextureBuffer[0]);
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
            DDSWriter writer        = DDSWriter.getNew(true, true, _width, _height);
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
            writer.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteHeaderException() {
        DDSHeader.Writer writer     = new DDSHeader.Writer();
        DDSHeader10.Writer header10 = new DDSHeader10.Writer();
        ByteBuffer buffer           = BufferUtils.getEmptyByteBuffer(DDSHeader.HEADER_LENGTH + DDSHeader.FILE_IDENTIFIER_LENGTH);

        // vérification du nombre de niveaux mipmap
        try {
            writer.setWidth(256);
            writer.setHeight(256);
            writer.setMipmapCount(2);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("9 mipmap levels must be set", e.getMessage().substring(0, 27));
        }

        // vérification du nombre de faces
        try {
            writer.setMipmapCount(1);
            writer.setCaps2(DDSHeader.DDSCAPS2_CUBEMAP | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("DDSCAPS2_CUBEMAP caps2 flag is set so all 6 faces must be defined", e.getMessage());
        }

        // vérification du nombre de faces
        try {
            writer.setCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("DDSCAPS2_CUBEMAP caps2 flag is not set so no face must be defined", e.getMessage());
        }

        // vérification des header DX10
        try {
            writer.setCaps2(0);
            writer.setPixelFormatFourCC(DDSFourCC.FOURCC_DX10);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("FourCC is set to FOURCC_DX10 so extended headers must be defined", e.getMessage());
        }

        // vérification des flags requis
        try {
            writer.setPixelFormatFourCC(0);
            writer.setFlags(DDSHeader.DDSD_CAPS | DDSHeader.DDSD_WIDTH | DDSHeader.DDSD_HEIGHT);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Flags DDSD_CAPS, DDSD_WIDTH, DDSD_HEIGHT and DDSD_PIXELFORMAT are required", e.getMessage());
        }

        // vérification du flag DDSD_MIPMAPCOUNT
        try {
            writer.addFlag(DDSHeader.DDSD_PIXELFORMAT);
            writer.addFlag(DDSHeader.DDSD_MIPMAPCOUNT);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Flag DDSD_MIPMAPCOUNT is set but mipmapCount", e.getMessage().substring(0, 44));
        }

        // vérification du flag DDSD_DEPTH
        try {
            writer.removeFlag(DDSHeader.DDSD_MIPMAPCOUNT);
            writer.addFlag(DDSHeader.DDSD_DEPTH);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Flag DDSD_DEPTH is set but 3D textures are not supported", e.getMessage());
        }

        // vérification du pitchOrLinearSize
        try {
            writer.removeFlag(DDSHeader.DDSD_DEPTH);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Either flag DDSD_PITCH or DDSD_LINEARSIZE must be defined", e.getMessage());
        }

        // vérification du pitchOrLinearSize
        try {
            writer.removeFlag(DDSHeader.DDSD_DEPTH);
            writer.addFlag(DDSHeader.DDSD_PITCH | DDSHeader.DDSD_LINEARSIZE);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Either flag DDSD_PITCH or DDSD_LINEARSIZE must be defined", e.getMessage());
        }

        // vérification des dimensions
        try {
            writer.removeFlag(DDSHeader.DDSD_PITCH);
            writer.setHeight(0);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Invalid width (", e.getMessage().substring(0, 15));
        }

        // vérification du caps flag DDSCAPS_TEXTURE
        try {
            writer.setHeight(256);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Caps flag DDSCAPS_TEXTURE is required", e.getMessage());
        }

        // vérification des caps flags DDSCAPS_MIPMAP et DDSCAPS_COMPLEX
        try {
            writer.addFlag(DDSHeader.DDSD_MIPMAPCOUNT);
            writer.setMipmapCount(9);
            writer.addCaps(DDSHeader.DDSCAPS_TEXTURE);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Flag DDSD_MIPMAPCOUNT is set, so caps flags DDSCAPS_MIPMAP and DDSCAPS_COMPLEX are required", e.getMessage());
        }

        // vérification du caps flags DDSCAPS_MIPMAP
        try {
            writer.removeFlag(DDSHeader.DDSD_MIPMAPCOUNT);
            writer.setMipmapCount(1);
            writer.addCaps(DDSHeader.DDSCAPS_MIPMAP);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Flag DDSD_MIPMAPCOUNT is not set, so caps flags DDSCAPS_MIPMAP must not be set", e.getMessage());
        }

        // vérification du caps flags DDSCAPS_COMPLEX
        try {
            writer.removeCaps(DDSHeader.DDSCAPS_MIPMAP);
            writer.addCaps2(DDSHeader.DDSCAPS2_CUBEMAP);
            writer.addCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ);
            writer.addCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX | DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY | DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Caps2 flag DDSCAPS2_CUBEMAP is set, so caps flags DDSCAPS_COMPLEX must be set", e.getMessage());
        }

        // vérification du array size
        try {
            writer.removeCaps2(DDSHeader.DDSCAPS2_CUBEMAP);
            writer.removeCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY | DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ);
            writer.removeCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX | DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY | DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ);
            writer.setPixelFormatFourCC(DDSFourCC.FOURCC_DX10);
            writer.setHeader10(header10);
            header10.setArraySize(2);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Extended headers array size is set to 2", e.getMessage().substring(0, 39));
        }

        // vérification du caps2 flags DDSCAPS2_VOLUME
        try {
            writer.setHeader10(null);
            writer.setPixelFormatFourCC(0);
            header10.setArraySize(0);
            writer.addCaps2(DDSHeader.DDSCAPS2_VOLUME);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Caps2 flag DDSCAPS2_VOLUME is set, but 3D textures are not supported", e.getMessage());
        }

        // vérification du fourCC
        try {
            writer.removeCaps2(DDSHeader.DDSCAPS2_VOLUME);
            writer.setPixelFormatFlags(DDSHeader.DDPF_FOURCC);
            writer.setPixelFormatFourCC(1);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Invalid fourCC value 0x", e.getMessage().substring(0, 23));
        }

        // vérification du RGBBitCount
        try {
            writer.removePixelFormatFlag(DDSHeader.DDPF_FOURCC);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Invalid RGB bitcount (0)", e.getMessage());
        }

        // vérification des masques R, G et B
        try {
            writer.addPixelFormatFlag(DDSHeader.DDPF_RGB);
            writer.setPixelFormatRgbBitCount(8);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Red, green and blue bitmasks must be defined", e.getMessage());
        }

        // vérification du masque alpha
        try {
            writer.removePixelFormatFlag(DDSHeader.DDPF_RGB);
            writer.addPixelFormatFlag(DDSHeader.DDPF_ALPHA);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Alpha bitmask must be defined", e.getMessage());
        }

        // vérification du masque alpha
        try {
            writer.removePixelFormatFlag(DDSHeader.DDPF_ALPHA);
            writer.addPixelFormatFlag(DDSHeader.DDPF_ALPHAPIXELS);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Alpha bitmask must be defined", e.getMessage());
        }

        // vérification du masque luminance
        try {
            writer.removePixelFormatFlag(DDSHeader.DDPF_ALPHAPIXELS);
            writer.addPixelFormatFlag(DDSHeader.DDPF_LUMINANCE);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Red bitmask must be defined", e.getMessage());
        }

        // vérification du misc flag des header DX10
        try {
            writer.removePixelFormatFlag(DDSHeader.DDPF_LUMINANCE);
            writer.addPixelFormatFlag(DDSHeader.DDPF_FOURCC);
            writer.setPixelFormatFourCC(DDSFourCC.FOURCC_DX10);
            writer.setHeader10(header10);
            header10.setResourceDimension(DDSHeader10.DDS_DIMENSION_TEXTURE2D);
            header10.addMiscFlag(DDSHeader10.DDS_RESOURCE_MISC_TEXTURECUBE);
            writer.write(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Extended headers indicate a cubemap, but not regular headers", e.getMessage());
        }
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
