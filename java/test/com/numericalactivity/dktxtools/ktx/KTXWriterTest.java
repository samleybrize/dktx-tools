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

    @Test
    public void testWriteUncompressedNoMipmap() {
        try {
            BufferedImage img       = ImageIO.read(new File("./testRes/texture.jpg"));
            short width             = (short) img.getWidth();
            short height            = (short) img.getHeight();
            int[] rgbArray          = new int[img.getWidth() * img.getHeight()];
            img.getRGB(0, 0, width, height, rgbArray, width * height - width, -width);
            ByteBuffer buffer       = BufferUtils.getPixelBuffer(rgbArray, TextureFormat.GL_RGBA);
            FileOutputStream out    = new FileOutputStream("./testRes/gen/uncompressed_no_mipmap.ktx");

            KTXWriter writer        = new KTXWriter(false, false, 256, 256);
            writer.setUncompressedFormat(TextureFormat.GL_RGBA);
            writer.getTextureData().set(0, buffer);
            writer.write(out);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
