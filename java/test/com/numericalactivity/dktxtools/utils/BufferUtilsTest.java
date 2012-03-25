package com.numericalactivity.dktxtools.utils;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

import com.numericalactivity.dktxtools.TextureFormat;

public class BufferUtilsTest {

    @Test
    public void testGetFloatBuffer() {
        float[] data        = {0.2f, 1.3f, 5.2f, 1.6f};
        FloatBuffer buffer  = BufferUtils.getFloatBuffer(data);

        assertEquals("Wrong capacity", data.length, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertEquals("Data mismatch", data[0], buffer.get(0), 0.01f);
        assertEquals("Data mismatch", data[1], buffer.get(1), 0.01f);
        assertEquals("Data mismatch", data[2], buffer.get(2), 0.01f);
        assertEquals("Data mismatch", data[3], buffer.get(3), 0.01f);
    }

    @Test
    public void testGetShortBuffer() {
        short[] data        = {2, 4, 8, 16};
        ShortBuffer buffer  = BufferUtils.getShortBuffer(data);

        assertEquals("Wrong capacity", data.length, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertEquals("Data mismatch", data[0], buffer.get(0));
        assertEquals("Data mismatch", data[1], buffer.get(1));
        assertEquals("Data mismatch", data[2], buffer.get(2));
        assertEquals("Data mismatch", data[3], buffer.get(3));
    }

    @Test
    public void testGetByteBufferByteArray() throws ArrayComparisonFailure, NoSuchAlgorithmException {
        byte[] data         = {2, 4, 8, 16};
        ByteBuffer buffer   = BufferUtils.getByteBuffer(data);

        assertEquals("Wrong capacity", data.length, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertArrayEquals("Invalid checksum", FileUtils.getChecksum(data), FileUtils.getChecksum(buffer));
    }

    @Test
    public void testGetByteBufferShortArray() {
        short[] data            = {2, 4, 8, 16};
        ByteBuffer buffer       = BufferUtils.getByteBuffer(data);
        ShortBuffer shortBuffer = buffer.asShortBuffer();

        assertEquals("Wrong capacity", data.length * 2, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertEquals("Data mismatch", data[0], shortBuffer.get(0));
        assertEquals("Data mismatch", data[1], shortBuffer.get(1));
        assertEquals("Data mismatch", data[2], shortBuffer.get(2));
        assertEquals("Data mismatch", data[3], shortBuffer.get(3));
    }

    @Test
    public void testGetByteBufferIntArray() {
        int[] data          = {2, 4, 8, 16};
        ByteBuffer buffer   = BufferUtils.getByteBuffer(data);
        IntBuffer intBuffer = buffer.asIntBuffer();

        assertEquals("Wrong capacity", data.length * 4, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertEquals("Data mismatch", data[0], intBuffer.get(0));
        assertEquals("Data mismatch", data[1], intBuffer.get(1));
        assertEquals("Data mismatch", data[2], intBuffer.get(2));
        assertEquals("Data mismatch", data[3], intBuffer.get(3));
    }

    @Test
    public void testGetByteBufferFloatArray() {
        float[] data            = {2.3f, 4.2f, 8.1f, 16.5f};
        ByteBuffer buffer       = BufferUtils.getByteBuffer(data);
        FloatBuffer floatBuffer = buffer.asFloatBuffer();

        assertEquals("Wrong capacity", data.length * 4, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
        assertEquals("Data mismatch", data[0], floatBuffer.get(0), 0.01f);
        assertEquals("Data mismatch", data[1], floatBuffer.get(1), 0.01f);
        assertEquals("Data mismatch", data[2], floatBuffer.get(2), 0.01f);
        assertEquals("Data mismatch", data[3], floatBuffer.get(3), 0.01f);
    }

    @Test
    public void testGetEmptyByteBuffer() {
        ByteBuffer buffer   = BufferUtils.getEmptyByteBuffer(6);

        assertEquals("Wrong capacity", 6, buffer.capacity());
        assertTrue("Buffer is not direct", buffer.isDirect());
    }

    @Test
    public void testGetPixelBuffer() {
        int[] pixels        = {0x88ddeeaa, 0xdd557733};
        byte[] components;
        ByteBuffer buffer;

        // rgba
        buffer      = BufferUtils.getPixelBuffer(pixels, TextureFormat.GL_RGBA);
        components  = new byte[pixels.length * 4];
        buffer.get(components);
        assertEquals("Wrong red component for color 1", (byte) 0xdd, components[0]);
        assertEquals("Wrong green component for color 1", (byte) 0xee, components[1]);
        assertEquals("Wrong blue component for color 1", (byte) 0xaa, components[2]);
        assertEquals("Wrong alpha component for color 1", (byte) 0x88, components[3]);
        assertEquals("Wrong red component for color 2", (byte) 0x55, components[4]);
        assertEquals("Wrong green component for color 2", (byte) 0x77, components[5]);
        assertEquals("Wrong blue component for color 2", (byte) 0x33, components[6]);
        assertEquals("Wrong alpha component for color 2", (byte) 0xdd, components[7]);

        // rgb
        buffer      = BufferUtils.getPixelBuffer(pixels, TextureFormat.GL_RGB);
        components  = new byte[pixels.length * 3];
        buffer.get(components);
        assertEquals("Wrong red component for color 1", (byte) 0xdd, components[0]);
        assertEquals("Wrong green component for color 1", (byte) 0xee, components[1]);
        assertEquals("Wrong blue component for color 1", (byte) 0xaa, components[2]);
        assertEquals("Wrong red component for color 2", (byte) 0x55, components[3]);
        assertEquals("Wrong green component for color 2", (byte) 0x77, components[4]);
        assertEquals("Wrong blue component for color 2", (byte) 0x33, components[5]);

        // luminance
        buffer      = BufferUtils.getPixelBuffer(pixels, TextureFormat.GL_LUMINANCE);
        components  = new byte[pixels.length];
        buffer.get(components);
        assertEquals("Wrong red component for color 1", (byte) 0xdd, components[0]);
        assertEquals("Wrong red component for color 2", (byte) 0x55, components[1]);

        // luminance alpha
        buffer      = BufferUtils.getPixelBuffer(pixels, TextureFormat.GL_LUMINANCE_ALPHA);
        components  = new byte[pixels.length * 2];
        buffer.get(components);
        assertEquals("Wrong red component for color 1", (byte) 0xdd, components[0]);
        assertEquals("Wrong alpha component for color 1", (byte) 0x88, components[1]);
        assertEquals("Wrong red component for color 2", (byte) 0x55, components[2]);
        assertEquals("Wrong alpha component for color 2", (byte) 0xdd, components[3]);

        // alpha
        buffer      = BufferUtils.getPixelBuffer(pixels, TextureFormat.GL_ALPHA);
        components  = new byte[pixels.length];
        buffer.get(components);
        assertEquals("Wrong alpha component for color 1", (byte) 0xdd, components[0]);
        assertEquals("Wrong alpha component for color 2", (byte) 0x55, components[1]);
    }

}
