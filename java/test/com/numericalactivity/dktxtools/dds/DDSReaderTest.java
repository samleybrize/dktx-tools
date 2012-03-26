package com.numericalactivity.dktxtools.dds;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;
import com.numericalactivity.dktxtools.utils.TextureUtils;

public class DDSReaderTest extends WriterTestAbstract {

    @Test
    public void testWriteUncompressedNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertTrue("Missing flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertFalse("Bad flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertFalse("Bad flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid pitch", 1024, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", 1, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertEquals("Invalid caps", DDSHeader.DDSCAPS_TEXTURE, headers.getCaps());
            assertEquals("Invalid caps2", 0, headers.getCaps2());
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());

            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertTrue("Missing pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertTrue("Missing pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertFalse("Bad pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", 0, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 32, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0xff, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0xff00, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0xff0000, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0xff000000, headers.getPixelFormatABitMask());

            // on teste l'integrité des données de texture
            int imageSize = DDSUtil.getUncompressedSize(_width, _height, headers.getPixelFormatRgbBitCount());
            _uncompressedTextureBuffer[0].position(0);
            assertTrue("md5 checksums not equal", FileUtils.isEqual(textureData.get(0), _uncompressedTextureBuffer[0]));
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getHeight(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertTrue("Missing flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertFalse("Bad flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertTrue("Missing flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid pitch", 1024, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", FILE_NUMBER_OF_MIPMAPS, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertTrue("Missing caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertEquals("Invalid caps2", 0, headers.getCaps2());
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());

            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertTrue("Missing pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertTrue("Missing pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertFalse("Bad pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", 0, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 32, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0xff, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0xff00, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0xff0000, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0xff000000, headers.getPixelFormatABitMask());

            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = DDSUtil.getUncompressedSize(width, height, headers.getPixelFormatRgbBitCount());

                _uncompressedTextureBuffer[i].position(0);
                assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i), FileUtils.isEqual(textureData.get(i), _uncompressedTextureBuffer[i]));
                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedCubemapNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertTrue("Missing flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertFalse("Bad flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertFalse("Bad flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid pitch", 1024, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", 1, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertFalse("Bad caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ));
            assertFalse("Bad caps2 flag DDSCAPS2_VOLUME", headers.hasCaps2(DDSHeader.DDSCAPS2_VOLUME));
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertTrue("Missing pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertTrue("Missing pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertFalse("Bad pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", 0, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 32, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0xff, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0xff00, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0xff0000, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0xff000000, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            for (byte f = 0; f < 6; f++) {
                _uncompressedTextureBuffer[0].position(0);
                assertTrue("md5 checksums not equal for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(0, f), _uncompressedTextureBuffer[0]));
            }

            int imageSize = DDSUtil.getUncompressedSize(_width, _height, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getWidth(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteUncompressedCubemapMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertTrue("Missing flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertFalse("Bad flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertTrue("Missing flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid pitch", 1024, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", FILE_NUMBER_OF_MIPMAPS, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertTrue("Missing caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ));
            assertFalse("Bad caps2 flag DDSCAPS2_VOLUME", headers.hasCaps2(DDSHeader.DDSCAPS2_VOLUME));
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertTrue("Missing pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertTrue("Missing pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertFalse("Bad pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", 0, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 32, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0xff, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0xff00, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0xff0000, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0xff000000, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                for (byte f = 0; f < 6; f++) {
                    _uncompressedTextureBuffer[i].position(0);
                    assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i) + " for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(i, f), _uncompressedTextureBuffer[i]));
                }

                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = DDSUtil.getUncompressedSize(width, height, headers.getPixelFormatRgbBitCount());

                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteCompressedNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_NO_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertFalse("Bad flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertTrue("Missing flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertFalse("Bad flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid linear size", 32768, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", 1, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertEquals("Invalid caps", DDSHeader.DDSCAPS_TEXTURE, headers.getCaps());
            assertEquals("Invalid caps2", 0, headers.getCaps2());
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertFalse("Bad pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertFalse("Bad pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertTrue("Missing pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", DDSFourCC.FOURCC_ETC1, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 0, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            int imageSize = DDSUtil.getCompressedSize(_width, _height, headers.getPixelFormatFourCC());
            _compressedTextureBuffer[0].position(0);
            assertTrue("md5 checksums not equal", FileUtils.isEqual(textureData.get(0), _compressedTextureBuffer[0]));
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getHeight(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertFalse("Bad flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertTrue("Missing flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertTrue("Missing flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid linear size", 32768, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", FILE_NUMBER_OF_MIPMAPS, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertTrue("Missing caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertEquals("Invalid caps2", 0, headers.getCaps2());
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertFalse("Bad pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertFalse("Bad pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertTrue("Missing pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", DDSFourCC.FOURCC_ETC1, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 0, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = DDSUtil.getCompressedSize(width, height, headers.getPixelFormatFourCC());

                _compressedTextureBuffer[i].position(0);
                assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i), FileUtils.isEqual(textureData.get(i), _compressedTextureBuffer[i]));
                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedCubemapNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertFalse("Bad flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertTrue("Missing flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertFalse("Bad flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid linear size", 32768, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", 1, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertFalse("Bad caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ));
            assertFalse("Bad caps2 flag DDSCAPS2_VOLUME", headers.hasCaps2(DDSHeader.DDSCAPS2_VOLUME));
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertFalse("Bad pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertFalse("Bad pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertTrue("Missing pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", DDSFourCC.FOURCC_ETC1, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 0, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            for (byte f = 0; f < 6; f++) {
                _compressedTextureBuffer[0].position(0);
                assertTrue("md5 checksums not equal for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(0, f), _compressedTextureBuffer[0]));
            }

            int imageSize = DDSUtil.getCompressedSize(_width, _height, headers.getPixelFormatFourCC());
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getHeight(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedCubemapMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(DDSWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP);
            DDSReader reader            = new DDSReader(in);
            DDSHeader headers           = reader.getHeaders();
            DDSTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid header length", DDSHeader.HEADER_LENGTH, headers.getSize());
            assertTrue("Missing flag DDSD_WIDTH", headers.hasFlags(DDSHeader.DDSD_WIDTH));
            assertTrue("Missing flag DDSD_HEIGHT", headers.hasFlags(DDSHeader.DDSD_HEIGHT));
            assertTrue("Missing flag DDSD_CAPS", headers.hasFlags(DDSHeader.DDSD_CAPS));
            assertTrue("Missing flag DDSD_PIXELFORMAT", headers.hasFlags(DDSHeader.DDSD_PIXELFORMAT));
            assertFalse("Bad flag DDSD_PITCH", headers.hasFlags(DDSHeader.DDSD_PITCH));
            assertFalse("Bad flag DDSD_DEPTH", headers.hasFlags(DDSHeader.DDSD_DEPTH));
            assertTrue("Missing flag DDSD_LINEARSIZE", headers.hasFlags(DDSHeader.DDSD_LINEARSIZE));
            assertTrue("Missing flag DDSD_MIPMAPCOUNT", headers.hasFlags(DDSHeader.DDSD_MIPMAPCOUNT));
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid linear size", 32768, headers.getPitchOrLinearSize());
            assertEquals("Invalid depth", 0, headers.getDepth());
            assertEquals("Invalid mipmap count", FILE_NUMBER_OF_MIPMAPS, headers.getMipmapCount());
            assertArrayEquals("Invalid reserved1", _ddsReserved1, headers.getReserved1());
            assertTrue("Missing caps flag DDSCAPS_TEXTURE", headers.hasCaps(DDSHeader.DDSCAPS_TEXTURE));
            assertTrue("Missing caps flag DDSCAPS_MIPMAP", headers.hasCaps(DDSHeader.DDSCAPS_MIPMAP));
            assertTrue("Missing caps flag DDSCAPS_COMPLEX", headers.hasCaps(DDSHeader.DDSCAPS_COMPLEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_NEGATIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_NEGATIVEZ));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEX", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEX));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEY", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEY));
            assertTrue("Missing caps2 flag DDSCAPS2_CUBEMAP_POSITIVEZ", headers.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP_POSITIVEZ));
            assertFalse("Bad caps2 flag DDSCAPS2_VOLUME", headers.hasCaps2(DDSHeader.DDSCAPS2_VOLUME));
            assertEquals("Invalid caps3", 0, headers.getCaps3());
            assertEquals("Invalid caps4", 0, headers.getCaps4());
            assertEquals("Invalid reserved2", 0, headers.getReserved2());
            
            assertEquals("Invalid pixel format length", DDSHeader.PIXEL_FORMAT_LENGTH, headers.getPixelFormatSize());
            assertFalse("Bad pixel format flag DDPF_ALPHAPIXELS", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHAPIXELS));
            assertFalse("Bad pixel format flag DDPF_RGB", headers.hasPixelFormatFlags(DDSHeader.DDPF_RGB));
            assertFalse("Bad pixel format flag DDPF_ALPHA", headers.hasPixelFormatFlags(DDSHeader.DDPF_ALPHA));
            assertTrue("Missing pixel format flag DDPF_FOURCC", headers.hasPixelFormatFlags(DDSHeader.DDPF_FOURCC));
            assertFalse("Bad pixel format flag DDPF_YUV", headers.hasPixelFormatFlags(DDSHeader.DDPF_YUV));
            assertFalse("Bad pixel format flag DDPF_LUMINANCE", headers.hasPixelFormatFlags(DDSHeader.DDPF_LUMINANCE));
            assertEquals("Invalid FourCC", DDSFourCC.FOURCC_ETC1, headers.getPixelFormatFourCC());
            assertEquals("Invalid RGB bit count", 0, headers.getPixelFormatRgbBitCount());
            assertEquals("Invalid R bitmask", 0, headers.getPixelFormatRBitMask());
            assertEquals("Invalid G bitmask", 0, headers.getPixelFormatGBitMask());
            assertEquals("Invalid B bitmask", 0, headers.getPixelFormatBBitMask());
            assertEquals("Invalid A bitmask", 0, headers.getPixelFormatABitMask());
            
            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                for (byte f = 0; f < 6; f++) {
                    _compressedTextureBuffer[i].position(0);
                    assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i) + " for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(i, f), _compressedTextureBuffer[i]));
                }

                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = DDSUtil.getCompressedSize(width, height, headers.getPixelFormatFourCC());

                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadHeaderException() {
        @SuppressWarnings("unused")
        DDSHeader reader;
        ByteBuffer buffer = BufferUtils.getEmptyByteBuffer(DDSHeader.HEADER_LENGTH + DDSHeader.FILE_IDENTIFIER_LENGTH);

        // une exception doit être lancée si l'identifiant du type de fichier DDS n'est pas bien formé
        try {
            reader = new DDSHeader.Reader(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Input doesn't start with DDS file identifier", e.getMessage());
        }

        // une exception doit être lancée si 'size' est invalide
        try {
            buffer.position(0);
            buffer.putInt(DDSHeader.FILE_IDENTIFIER);
            buffer.putInt(2);
            buffer.position(0);
    
            reader = new DDSHeader.Reader(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Unexpected size for headers", e.getMessage().substring(0, 27));
        }

        // une exception doit être lancée si glTypeSize est invalide
        try {
            buffer.position(0);
            buffer.putInt(DDSHeader.FILE_IDENTIFIER);
            buffer.putInt(DDSHeader.HEADER_LENGTH);
    
            for (byte i = 0; i < 18; i++) {
                buffer.putInt(0);
            }
    
            buffer.position(0);
            reader = new DDSHeader.Reader(buffer);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Unexpected size for pixel format", e.getMessage().substring(0, 32));
        }
    }

    @Test
    public void testReadTextureDataException() throws IOException {
        // une exception doit être lancée si la texture est un cubemap et qu'aucune face n'est définie
        try {
            @SuppressWarnings("unused")
            DDSTextureData textureData;

            DDSHeader.Writer header = new DDSHeader.Writer();
            header.setCaps2(DDSHeader.DDSCAPS2_CUBEMAP);

            textureData = new DDSTextureData.Reader(null, header);
            fail("DDSFormatException expected");
        } catch (DDSFormatException e) {
            assertEquals("Header 'DDSCAPS2' not properly defined. It indicates a cube map, but no faces.", e.getMessage());
        }
    }
}
