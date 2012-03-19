package com.numericalactivity.dktxtools.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Utilitaire pour les buffers
 */
abstract public class BufferUtils {
    /**
     * Converti un tableau de float en FloatBuffer
     * @param array
     * @return
     */
    public static FloatBuffer getFloatBuffer(float[] array) {
        FloatBuffer buffer = ByteBuffer
            .allocateDirect(array.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        ;
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    /**
     * Converti un tableau de short en ShortBuffer
     * @param array
     * @return
     */
    public static ShortBuffer getShortBuffer(short[] array) {
        ShortBuffer buffer = ByteBuffer
            .allocateDirect(array.length * 2)
            .order(ByteOrder.nativeOrder())
            .asShortBuffer()
        ;
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    /**
     * Converti un tableau de byte en ByteBuffer
     * @param array
     * @return
     */
    public static ByteBuffer getByteBuffer(byte[] array) {
        ByteBuffer buffer = ByteBuffer
            .allocateDirect(array.length)
            .order(ByteOrder.nativeOrder())
        ;
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    /**
     * Converti un tableau de short en ByteBuffer
     * @param array
     * @return
     */
    public static ByteBuffer getByteBuffer(short[] array) {
        ByteBuffer byteBuffer   = ByteBuffer
            .allocateDirect(array.length * 4)
            .order(ByteOrder.nativeOrder())
        ;
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(array);
        shortBuffer.position(0);
        byteBuffer.position(0);
        return byteBuffer;
    }

    /**
     * Converti un tableau d'integer en ByteBuffer
     * @param array
     * @return
     */
    public static ByteBuffer getByteBuffer(int[] array) {
        ByteBuffer byteBuffer   = ByteBuffer
            .allocateDirect(array.length * 4)
            .order(ByteOrder.nativeOrder())
        ;
        IntBuffer intBuffer     = byteBuffer.asIntBuffer();
        intBuffer.put(array);
        intBuffer.position(0);
        byteBuffer.position(0);
        return byteBuffer;
    }

    /**
     * Converti un tableau de float en ByteBuffer
     * @param array
     * @return
     */
    public static ByteBuffer getByteBuffer(float[] array) {
        ByteBuffer byteBuffer   = ByteBuffer
            .allocateDirect(array.length * 4)
            .order(ByteOrder.nativeOrder())
        ;
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(array);
        floatBuffer.position(0);
        byteBuffer.position(0);
        return byteBuffer;
    }

    /**
     * Créer un ByteBuffer et alloue 'size' bytes
     * @param size
     * @return
     */
    public static ByteBuffer getEmptyByteBuffer(int size) {
        ByteBuffer buffer = ByteBuffer
            .allocateDirect(size)
            .order(ByteOrder.nativeOrder())
        ;
        buffer.position(0);
        return buffer;
    }
}
