package com.numericalactivity.dktxtools.test;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.numericalactivity.dktxtools.TextureFormat;
import com.numericalactivity.dktxtools.utils.BufferUtils;

abstract public class WriterTestAbstract {

    protected static final byte FILE_NUMBER_OF_MIPMAPS              = 9;
    protected static final String FILE_UNCOMPRESSED_TEXTURE_BASE    = "./testRes/uncompressed/texture";
    protected static final String FILE_COMPRESSED_TEXTURE_BASE      = "./testRes/compressed/texture";

    protected static short _width;
    protected static short _height;
    protected static ByteBuffer[] _uncompressedTextureBuffer;
    protected static ByteBuffer[] _compressedTextureBuffer;
    protected static byte[] _metadataBytes = {2, 5, 8, 6};

    /**
     * Charge les données des textures
     */
    public WriterTestAbstract() {
        // on ne charge les données qu'une seule fois
        if (null != _uncompressedTextureBuffer) {
            return;
        }

        try {
            _uncompressedTextureBuffer  = new ByteBuffer[FILE_NUMBER_OF_MIPMAPS];
            _compressedTextureBuffer    = new ByteBuffer[FILE_NUMBER_OF_MIPMAPS];
            BufferedImage img;
            File file;
            FileInputStream in;
            short width;
            short height;
            int[] rgbArray;
            byte[] compressedData;

            // on récupère les données de chaque niveau mipmap
            for (byte i = 0; i < FILE_NUMBER_OF_MIPMAPS; i++) {
                // texture non compressée
                img                             = ImageIO.read(new File(FILE_UNCOMPRESSED_TEXTURE_BASE + String.valueOf(i) + ".jpg"));
                width                           = (short) img.getWidth();
                height                          = (short) img.getHeight();
                rgbArray                        = new int[img.getWidth() * img.getHeight()];
                img.getRGB(0, 0, width, height, rgbArray, width * height - width, -width);
                _uncompressedTextureBuffer[i]   = BufferUtils.getPixelBuffer(rgbArray, TextureFormat.GL_RGBA);

                // texture compressée
                file                            = new File(FILE_COMPRESSED_TEXTURE_BASE + String.valueOf(i) + ".etc");
                in                              = new FileInputStream(file);
                compressedData                  = new byte[(int) file.length()];
                in.read(compressedData);
                _compressedTextureBuffer[i]     = BufferUtils.getByteBuffer(compressedData);

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
}
