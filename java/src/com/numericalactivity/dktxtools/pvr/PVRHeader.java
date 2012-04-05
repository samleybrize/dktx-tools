package com.numericalactivity.dktxtools.pvr;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.numericalactivity.dktxtools.utils.BufferUtils;
import com.numericalactivity.dktxtools.utils.FlagsUtils;

/**
 * Classe de gestion des entêtes d'un fichier PVR
 */
public abstract class PVRHeader {
    public static final int HEADER_LENGTH       = 52; // taille de l'entête
    public static final int ENDIANNESS_OK       = 0x50565203; // endianness qui correspond à celui du programme qui lit le fichier
    public static final int ENDIANNESS_OPPOSITE = 0x03525650; // endianness opposé

    boolean _byteOrderNative;
    ByteOrder _byteOrder;
    int _flags;
    int _pixelFormat1;
    int _pixelFormat2;
    int _colorSpace;
    int _channelType;
    int _height;
    int _width;
    int _depth;
    int _numberOfSurfaces;
    int _numberOfFaces          = 1;
    int _numberOfMipmapLevels   = 1;
    int _metadataSize;

    // TODO getters
    // TODO setters
    // TODO reset
    public void reset() {
        
    }

    @Override
    public String toString() {
        return String.format(
            "%s\n    flags=[%s]\n    pixelFormat1=%d\n    pixelFormat2=%d\n    colourSPace=%d\n    channelType=%d\n    width=%d\n    height=%d\n    depth=%d\n    numberOfSurfaces=%d\n    numberOfFaces=%d\n    numberOfMipmapLevels=%d\n    metadataSize=%d",
            getClass().getCanonicalName(), FlagsUtils.toHexString(FlagsUtils.getFlags(_flags)), _pixelFormat1, _pixelFormat2, _colorSpace, _channelType, _height, _width, _depth, _numberOfSurfaces, _numberOfFaces,
            _numberOfMipmapLevels, _metadataSize
        );
    }

    /**
     * Une classe qui permet de lire les entêtes d'un fichier PVR
     */
    public static class Reader extends PVRHeader {
        /**
         * Constructeur
         */
        Reader() {
        }

        /**
         * Constructeur.
         * Lit les entêtes du fichier.
         * @param in le pointeur doit être placé au début du fichier
         * @throws IOException
         * @throws PVRFormatException
         */
        Reader(BufferedInputStream in) throws IOException, PVRFormatException {
            read(in);
        }

        /**
         * Constructeur.
         * Lit les entêtes du fichier.
         * @param buffer buffer contenant les données des entêtes. Les données doivent être placées au début du buffer, ou la position du buffer doit être définie au début des données des entêtes. D'autres données peuvent être présentes à la suite des entêtes sans incidence.
         * @throws PVRFormatException
         */
        Reader(ByteBuffer buffer) throws PVRFormatException {
            read(buffer);
        }

        /**
         * Lit les entêtes du fichier
         * @param in le pointeur doit être placé au début du fichier
         * @throws IOException 
         * @throws PVRFormatException 
         */
        void read(BufferedInputStream in) throws IOException, PVRFormatException {
            ByteBuffer buffer   = BufferUtils.getEmptyByteBuffer(HEADER_LENGTH);
            byte[] data         = new byte[HEADER_LENGTH];
            in.read(data, 0, HEADER_LENGTH);
            buffer.put(data);
            buffer.position(0);
            read(buffer);
        }

        /**
         * Lit les entêtes du fichier
         * @param buffer buffer contenant les données des entêtes. Les données doivent être placées au début du buffer, ou la position du buffer doit être définie au début des données des entêtes. D'autres données peuvent être présentes à la suite des entêtes sans incidence.
         * @throws PVRFormatException 
         */
        void read(ByteBuffer buffer) throws PVRFormatException {
            // on garde en mémoire l'ordre actuel du ByteBuffer
            ByteOrder oldOrder = buffer.order();

            // on défini l'ordre du buffer à l'ordre natif
            buffer.order(ByteOrder.nativeOrder());

            // on vérifie l'endianess et modifie le sens du buffer si nécessaire
            int endianness  = buffer.getInt();
            _byteOrder      = buffer.order();

            if (endianness == ENDIANNESS_OK) {
                // endianness natif
                _byteOrderNative    = true;
            } else if (endianness == ENDIANNESS_OPPOSITE) {
                // endianness inversé
                _byteOrderNative    = false;
                _byteOrder          = (ByteOrder.BIG_ENDIAN == _byteOrder) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
                buffer.order(_byteOrder);
            } else {
                throw new PVRFormatException(String.format("Endianness field has an unexpected value: %08x", endianness));
            }

            // on récupère le reste des entêtes
            _flags                  = buffer.getInt();
            _pixelFormat1           = buffer.getInt();
            _pixelFormat2           = buffer.getInt();
            _colorSpace             = buffer.getInt();
            _channelType            = buffer.getInt();
            _height                 = buffer.getInt();
            _width                  = buffer.getInt();
            _depth                  = buffer.getInt();
            _numberOfSurfaces       = buffer.getInt();
            _numberOfFaces          = buffer.getInt();
            _numberOfMipmapLevels   = buffer.getInt();
            _metadataSize           = buffer.getInt();

            // on contrôle que pixelFormat est bien un format pvrtc
            if (PVRPixelFormat.PVRTC2BPP_RGB != _pixelFormat1
                    && PVRPixelFormat.PVRTC2BPP_RGBA != _pixelFormat1
                    && PVRPixelFormat.PVRTC4BPP_RGB != _pixelFormat1
                    && PVRPixelFormat.PVRTC4BPP_RGBA != _pixelFormat1) {
                throw new PVRFormatException("Pixel format " + String.valueOf(_pixelFormat1) + " is not supported");
            }

            // on remet le buffer à son ordre d'origine
            buffer.order(oldOrder);
        }
    }
}
