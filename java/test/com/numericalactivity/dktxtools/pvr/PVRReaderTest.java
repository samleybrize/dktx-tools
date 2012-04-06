package com.numericalactivity.dktxtools.pvr;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;
import com.numericalactivity.dktxtools.utils.TextureUtils;

public class PVRReaderTest extends WriterTestAbstract {

    public static final String FILE_COMPRESSED_NO_MIPMAP                = "./testRes/pvr/compressed_no_mipmap.pvr";
    public static final String FILE_COMPRESSED_MIPMAP                   = "./testRes/pvr/compressed_mipmap.pvr";
    public static final String FILE_COMPRESSED_CUBEMAP_NO_MIPMAP        = "./testRes/pvr/compressed_cubemap_no_mipmap.pvr";
    public static final String FILE_COMPRESSED_CUBEMAP_MIPMAP           = "./testRes/pvr/compressed_cubemap_mipmap.pvr";

    @Test
    public void testWriteCompressedNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(FILE_COMPRESSED_NO_MIPMAP);
            PVRReader reader            = PVRReader.getNew(in);
            PVRHeader headers           = reader.getHeaders();
            PVRTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid flags", 0, headers.getFlags());
            assertEquals("Invalid pixel format 1", PVRPixelFormat.PVRTC4BPP_RGBA, headers.getPixelFormat1());
            assertEquals("Invalid pixel format 2", 0, headers.getPixelFormat2());
            assertEquals("Invalid colour space", 0, headers.getColourSpace());
            assertEquals("Invalid channel type", 0, headers.getChannelType());
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid depth", 1, headers.getDepth());
            assertEquals("Invalid number of surfaces", 1, headers.getNumberOfSurfaces());
            assertEquals("Invalid number of faces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid number of mipmaps", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid metadata size", 15, headers.getMetadataSize());
            
            // on teste l'integrité des données de texture
            int imageSize = PVRUtil.getCompressedSize(_width, _height, headers.getPixelFormat1());
            _compressedPvrtcTextureBuffer[0].position(0);
            assertTrue("md5 checksums not equal", FileUtils.isEqual(textureData.get(0), _compressedPvrtcTextureBuffer[0]));
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getHeight(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());

            reader.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(FILE_COMPRESSED_MIPMAP);
            PVRReader reader            = PVRReader.getNew(in);
            PVRHeader headers           = reader.getHeaders();
            PVRTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid flags", 0, headers.getFlags());
            assertEquals("Invalid pixel format 1", PVRPixelFormat.PVRTC4BPP_RGBA, headers.getPixelFormat1());
            assertEquals("Invalid pixel format 2", 0, headers.getPixelFormat2());
            assertEquals("Invalid colour space", 0, headers.getColourSpace());
            assertEquals("Invalid channel type", 0, headers.getChannelType());
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid depth", 1, headers.getDepth());
            assertEquals("Invalid number of surfaces", 1, headers.getNumberOfSurfaces());
            assertEquals("Invalid number of faces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid number of mipmaps", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid metadata size", 15, headers.getMetadataSize());
            
            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = PVRUtil.getCompressedSize(width, height, headers.getPixelFormat1());

                _compressedPvrtcTextureBuffer[i].position(0);
                assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i), FileUtils.isEqual(textureData.get(i), _compressedPvrtcTextureBuffer[i]));
                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 1, textureData.getNumberOfFaces());

            reader.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedCubemapNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
            PVRReader reader            = PVRReader.getNew(in);
            PVRHeader headers           = reader.getHeaders();
            PVRTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid flags", 0, headers.getFlags());
            assertEquals("Invalid pixel format 1", PVRPixelFormat.PVRTC4BPP_RGBA, headers.getPixelFormat1());
            assertEquals("Invalid pixel format 2", 0, headers.getPixelFormat2());
            assertEquals("Invalid colour space", 0, headers.getColourSpace());
            assertEquals("Invalid channel type", 0, headers.getChannelType());
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid depth", 1, headers.getDepth());
            assertEquals("Invalid number of surfaces", 1, headers.getNumberOfSurfaces());
            assertEquals("Invalid number of faces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid number of mipmaps", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid metadata size", 15, headers.getMetadataSize());
            
            // on teste l'integrité des données de texture
            for (byte f = 0; f < 6; f++) {
                _compressedPvrtcTextureBuffer[0].position(0);
                assertTrue("md5 checksums not equal for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(0, f), _compressedPvrtcTextureBuffer[0]));
            }

            int imageSize = PVRUtil.getCompressedSize(_width, _height, headers.getPixelFormat1());
            assertEquals("Invalid width in texture data", _width, textureData.getWidth(0));
            assertEquals("Invalid height in texture data", _height, textureData.getHeight(0));
            assertEquals("Invalid image size", imageSize, textureData.getImageSize(0));
            assertEquals("Invalid number of mipmap levels", 1, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());

            reader.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testWriteCompressedCubemapMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(FILE_COMPRESSED_CUBEMAP_MIPMAP);
            PVRReader reader            = PVRReader.getNew(in);
            PVRHeader headers           = reader.getHeaders();
            PVRTextureData textureData  = reader.getTextureData();
            
            // on valide les entêtes
            assertEquals("Invalid flags", 0, headers.getFlags());
            assertEquals("Invalid pixel format 1", PVRPixelFormat.PVRTC4BPP_RGBA, headers.getPixelFormat1());
            assertEquals("Invalid pixel format 2", 0, headers.getPixelFormat2());
            assertEquals("Invalid colour space", 0, headers.getColourSpace());
            assertEquals("Invalid channel type", 0, headers.getChannelType());
            assertEquals("Invalid width", _width, headers.getWidth());
            assertEquals("Invalid height", _height, headers.getHeight());
            assertEquals("Invalid depth", 1, headers.getDepth());
            assertEquals("Invalid number of surfaces", 1, headers.getNumberOfSurfaces());
            assertEquals("Invalid number of faces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid number of mipmaps", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid metadata size", 15, headers.getMetadataSize());
            
            // on teste l'integrité des données de texture
            int imageSize;
            int width;
            int height;

            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                for (byte f = 0; f < 6; f++) {
                    _compressedPvrtcTextureBuffer[i].position(0);
                    assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i) + " for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(i, f), _compressedPvrtcTextureBuffer[i]));
                }

                width       = TextureUtils.getDimensionForMipmapLevel(i, _width);
                height      = TextureUtils.getDimensionForMipmapLevel(i, _height);
                imageSize   = PVRUtil.getCompressedSize(width, height, headers.getPixelFormat1());

                assertEquals("Invalid width in texture data for mipmap level " + String.valueOf(i), width, textureData.getWidth(i));
                assertEquals("Invalid height in texture data for mipmap level " + String.valueOf(i), height, textureData.getHeight(i));
                assertEquals("Invalid image size for mipmap level " + String.valueOf(i), imageSize, textureData.getImageSize(i));
            }

            assertEquals("Invalid number of mipmap levels", FILE_NUMBER_OF_MIPMAPS, textureData.getNumberOfMipmapLevels());
            assertEquals("Invalid number of faces", 6, textureData.getNumberOfFaces());

            reader.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadHeaderException() {
        @SuppressWarnings("unused")
        PVRHeader reader;
        ByteBuffer buffer = BufferUtils.getEmptyByteBuffer(PVRHeader.HEADER_LENGTH);

        // une exception doit être lancée si l'entier de vérification de l'endianness n'est pas valide
        try {
            reader = new PVRHeader.Reader(buffer);
            fail("PVRFormatException expected");
        } catch (PVRFormatException e) {
            assertEquals("Endianness field has an unexpected value", e.getMessage().substring(0, 40));
        }

        // une exception doit être lancée si le pixel format 1 n'est pas valide
        try {
            buffer.position(0);
            buffer.putInt(PVRHeader.ENDIANNESS_OK);
            buffer.putInt(0);
            buffer.putInt(240);
            buffer.position(0);

            reader = new PVRHeader.Reader(buffer);
            fail("PVRFormatException expected");
        } catch (PVRFormatException e) {
            assertEquals("Pixel format 240 is not supported", e.getMessage());
        }
    }
}
