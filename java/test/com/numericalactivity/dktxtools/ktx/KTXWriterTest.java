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

public class KTXWriterTest {

    protected static final byte FILE_NUMBER_OF_MIPMAPS              = 9;
    protected static final String FILE_UNCOMPRESSED_TEXTURE_BASE    = "./testRes/uncompressed/texture";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP       = "./testRes/ktx/uncompressed_no_mipmap.ktx";
    protected static final String FILE_UNCOMPRESSED_NO_MIPMAP_GEN   = "./testRes/gen/uncompressed_no_mipmap.ktx";

    protected ByteBuffer[] _uncompressedTextureBuffer;

    public KTXWriterTest() {
        try {
            _uncompressedTextureBuffer = new ByteBuffer[FILE_NUMBER_OF_MIPMAPS];
            BufferedImage img;
            short width;
            short height;
            int[] rgbArray;

            for (byte i = 0; i < 9; i++) {
                img                             = ImageIO.read(new File(FILE_UNCOMPRESSED_TEXTURE_BASE + String.valueOf(i) + ".jpg"));
                width                           = (short) img.getWidth();
                height                          = (short) img.getHeight();
                rgbArray                        = new int[img.getWidth() * img.getHeight()];
                img.getRGB(0, 0, width, height, rgbArray, width * height - width, -width);
                _uncompressedTextureBuffer[i]   = BufferUtils.getPixelBuffer(rgbArray, TextureFormat.GL_RGBA);
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
            KTXWriter writer        = new KTXWriter(false, false, 256, 256);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, _uncompressedTextureBuffer[0]);
            writer.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
