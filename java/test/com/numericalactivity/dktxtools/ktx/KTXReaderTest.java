package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.io.FileInputStream;

import org.junit.Test;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.test.WriterTestAbstract;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class KTXReaderTest extends WriterTestAbstract {

    @Test
    public void testReadUncompressedNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_UNCOMPRESSED_NO_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", TextureFormat.GL_UNSIGNED_BYTE, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", TextureFormat.GL_RGBA, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_RGBA, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGBA, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            _uncompressedTextureBuffer[0].position(0);
            assertTrue("md5 checksums not equal", FileUtils.isEqual(textureData.get(0), _uncompressedTextureBuffer[0]));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadUncompressedMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_UNCOMPRESSED_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", TextureFormat.GL_UNSIGNED_BYTE, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", TextureFormat.GL_RGBA, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_RGBA, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGBA, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                _uncompressedTextureBuffer[i].position(0);
                assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i), FileUtils.isEqual(textureData.get(i), _uncompressedTextureBuffer[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadUncompressedCubemapNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", TextureFormat.GL_UNSIGNED_BYTE, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", TextureFormat.GL_RGBA, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_RGBA, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGBA, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte f = 0; f < 6; f++) {
                _uncompressedTextureBuffer[0].position(0);
                assertTrue("md5 checksums not equal for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(0, f), _uncompressedTextureBuffer[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadUncompressedCubemapMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_UNCOMPRESSED_CUBEMAP_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", TextureFormat.GL_UNSIGNED_BYTE, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", TextureFormat.GL_RGBA, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_RGBA, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGBA, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                for (byte f = 0; f < 6; f++) {
                    _uncompressedTextureBuffer[i].position(0);
                    assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i) + " for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(i, f), _uncompressedTextureBuffer[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadCompressedNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_COMPRESSED_NO_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", 0, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", 0, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_ETC1_RGB8, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGB, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            _compressedTextureBuffer[0].position(0);
            assertTrue("md5 checksums not equal", FileUtils.isEqual(textureData.get(0), _compressedTextureBuffer[0]));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadCompressedMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_COMPRESSED_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", 0, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", 0, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_ETC1_RGB8, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGB, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 1, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                _compressedTextureBuffer[i].position(0);
                assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i), FileUtils.isEqual(textureData.get(i), _compressedTextureBuffer[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadCompressedCubemapNoMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_NO_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", 0, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", 0, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_ETC1_RGB8, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGB, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", 1, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte f = 0; f < 6; f++) {
                _compressedTextureBuffer[0].position(0);
                assertTrue("md5 checksums not equal for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(0, f), _compressedTextureBuffer[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testReadCompressedCubemapMipmap() {
        try {
            // on charge le fichier
            FileInputStream in          = new FileInputStream(KTXWriterTest.FILE_COMPRESSED_CUBEMAP_MIPMAP);
            KTXReader reader            = new KTXReader(in);
            KTXHeader headers           = reader.getHeaders();
            KTXTextureData textureData  = reader.getTextureData();

            // on valide les entêtes
            assertEquals("Invalid glType", 0, headers.getGLType());
            assertEquals("Invalid glTypeSize", 1, headers.getGLTypeSize());
            assertEquals("Invalid glFormat", 0, headers.getGLFormat());
            assertEquals("Invalid glInternalFormat", TextureFormat.GL_ETC1_RGB8, headers.getGLInternalFormat());
            assertEquals("Invalid glBaseInternalFormat", TextureFormat.GL_RGB, headers.getGLBaseInternalFormat());
            assertEquals("Invalid width", _width, headers.getPixelWidth());
            assertEquals("Invalid height", _height, headers.getPixelHeight());
            assertEquals("Invalid depth", 0, headers.getPixelDepth());
            assertEquals("Invalid numberOfArrayElements", 0, headers.getNumberOfArrayElements());
            assertEquals("Invalid numberOfFaces", 6, headers.getNumberOfFaces());
            assertEquals("Invalid numberOfMipmapLevels", FILE_NUMBER_OF_MIPMAPS, headers.getNumberOfMipmapLevels());
            assertEquals("Invalid bytesOfKeyValueData", 48, headers.getBytesOfKeyValueData());

            // on valide les métadonnées
            assertEquals("Invalid metadata string", "testValue", reader.getMetadata().getString("KTXTest"));
            assertEquals("Invalid metadata bytes", _metadataBytes, reader.getMetadata().get("KTXTestBytes"));

            // on teste l'integrité des données de texture
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                for (byte f = 0; f < 6; f++) {
                    _compressedTextureBuffer[i].position(0);
                    assertTrue("md5 checksums not equal for mipmap level " + String.valueOf(i) + " for face " + String.valueOf(f), FileUtils.isEqual(textureData.get(i, f), _compressedTextureBuffer[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
