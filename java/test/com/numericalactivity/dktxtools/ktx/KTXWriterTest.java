package com.numericalactivity.dktxtools.ktx;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FileUtils;

public class KTXWriterTest {

    protected static final byte FILE_NUMBER_OF_MIPMAPS                      = 9;
    protected static final String FILE_UNCOMPRESSED_TEXTURE_BASE            = "./testRes/uncompressed/texture";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP               = "./testRes/ktx/uncompressed_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP_GEN           = "./testRes/gen/uncompressed_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_MIPMAP                  = "./testRes/ktx/uncompressed_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_MIPMAP_GEN              = "./testRes/gen/uncompressed_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP       = "./testRes/ktx/uncompressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN   = "./testRes/gen/uncompressed_cubemap_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP          = "./testRes/ktx/uncompressed_cubemap_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN      = "./testRes/gen/uncompressed_cubemap_mipmap.ktx";

    protected short _width;
    protected short _height;
    protected ByteBuffer[] _uncompressedTextureBuffer;

    /**
     * Constructeur.
     * Charge les données des textures.
     */
    public KTXWriterTest() {
        try {
            _uncompressedTextureBuffer = new ByteBuffer[FILE_NUMBER_OF_MIPMAPS];
            BufferedImage img;
            short width;
            short height;
            int[] rgbArray;

            // on récupère les données de chaque niveau mipmap
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                // texture non compressée
                img                             = ImageIO.read(new File(FILE_UNCOMPRESSED_TEXTURE_BASE + String.valueOf(i) + ".jpg"));
                width                           = (short) img.getWidth();
                height                          = (short) img.getHeight();
                rgbArray                        = new int[img.getWidth() * img.getHeight()];
                img.getRGB(0, 0, width, height, rgbArray, width * height - width, -width);
                _uncompressedTextureBuffer[i]   = BufferUtils.getPixelBuffer(rgbArray, TextureFormat.GL_RGBA);

                // TODO texture compressée

                // on retient les dimensions du niveau mipmap 0
                if (0 == i) {
                    _width  = width;
                    _height = height;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteUncompressedNoMipmap() {
        try {
            FileOutputStream out    = new FileOutputStream(FILE_UNCOMPRESSED_NO_MIPMAP_GEN);
            KTXWriter writer        = new KTXWriter(false, false, _width, _height);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
            writer.write(out);

            assertTrue(FileUtils.isEqual(FILE_UNCOMPRESSED_NO_MIPMAP, FILE_UNCOMPRESSED_NO_MIPMAP_GEN));
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

            writer.write(out);

            assertTrue(FileUtils.isEqual(FILE_UNCOMPRESSED_MIPMAP, FILE_UNCOMPRESSED_MIPMAP_GEN));
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
            writer.write(out);

            assertTrue(FileUtils.isEqual(FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP, FILE_UNCOMPRESSED_CUBEMAP_NO_MIPMAP_GEN));
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

            writer.write(out);

            assertTrue(FileUtils.isEqual(FILE_UNCOMPRESSED_CUBEMAP_MIPMAP, FILE_UNCOMPRESSED_CUBEMAP_MIPMAP_GEN));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
