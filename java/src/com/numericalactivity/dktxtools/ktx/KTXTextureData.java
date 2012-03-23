package com.numericalactivity.dktxtools.ktx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.numericalactivity.dktxtools.utils.TextureUtils;

/**
 * Classe de gestion des données des textures contenues dans un fichier KTX
 */
public abstract class KTXTextureData {

    protected ByteBuffer[][] _textureData; // données des textures classées par niveau mipmap puis par face
    protected int[] _imageSize; // taille des images classées par niveau mipmap
    protected short[] _width; // largeur des images classées par niveau mipmap
    protected short[] _height; // hauteur des images classées par niveau mipmap
    protected byte _numberOfMipmapLevels; // nombre de niveaux mipmap
    protected byte _numberOfFaces; // nombre de face par niveau mipmap

    /**
     * Retourne le nombre de niveaux mipmap
     * @return
     */
    public int getNumberOfMipmapLevels() {
        return _numberOfMipmapLevels;
    }

    /**
     * Retourne le nombre de face par niveau mipmap
     * @return
     */
    public int getNumberOfFaces() {
        return _numberOfFaces;
    }

    /**
     * Retourne la taille des données (en bytes) de l'image d'un niveau mipmap
     * @param mipmapLevel
     * @return
     */
    public int getImageSize(int mipmapLevel) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        }

        return _imageSize[mipmapLevel];
    }

    /**
     * Retourne la taille (en bytes) de chaque niveau mipmap
     * @return
     */
    public int[] getAllImageSizes() {
        return _imageSize;
    }

    /**
     * Retourne la largeur d'un niveau mipmap
     * @param mipmapLevel
     * @return
     */
    public int getWidth(int mipmapLevel) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        }
        
        return _width[mipmapLevel];
    }

    /**
     * Retourne la hauteur d'un niveau mipmap
     * @param mipmapLevel
     * @return
     */
    public int getHeight(int mipmapLevel) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        }
        
        return _height[mipmapLevel];
    }

    /**
     * Retourne la largeur de chaque niveau mipmap
     * @return
     */
    public short[] getAllWidth() {
        return _width;
    }

    /**
     * Retourne la hauteur de chaque niveau mipmap
     * @return
     */
    public short[] getAllHeight() {
        return _height;
    }

    /**
     * Retourne le buffer contenant l'image correspondant au niveau mipmap 'mipmapLevel'
     * @param mipmapLevel
     * @return
     */
    public ByteBuffer get(int mipmapLevel) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        }

        return _textureData[mipmapLevel][0];
    }

    /**
     * Retourne le buffer contenant l'image correspondant au niveau mipmap 'mipmapLevel' et à la face 'face'
     * @param mipmapLevel
     * @param face
     * @return
     */
    public ByteBuffer get(int mipmapLevel, int face) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        } else if (face > _numberOfFaces - 1) {
            throw new ArrayIndexOutOfBoundsException("No face " + String.valueOf(mipmapLevel));
        }
        
        return _textureData[mipmapLevel][face];
    }

    /**
     * Retourne les buffers contenant les images de la première face de chaque niveau mipmap
     * @return
     */
    public ByteBuffer[] getAllOneFace() {
        ByteBuffer[] buffers = new ByteBuffer[_numberOfMipmapLevels];

        for (byte i = 0; i < _numberOfMipmapLevels; i++) {
            buffers[i] = _textureData[i][0];
        }

        return buffers;
    }

    public void setNumberOfMipmapLevels(int numberOfMipmapLevels) throws KTXFormatException {
        setNumberOfMipmapLevelsAndFaces(numberOfMipmapLevels, _numberOfFaces);
    }

    public void setNumberOfFaces(int numberOfFaces) throws KTXFormatException {
        setNumberOfMipmapLevelsAndFaces(_numberOfMipmapLevels, numberOfFaces);
    }

    public void setNumberOfMipmapLevelsAndFaces(int numberOfMipmapLevels, int numberOfFaces) throws KTXFormatException {
        numberOfMipmapLevels    = (numberOfMipmapLevels == 0) ? 1 : numberOfMipmapLevels;
        numberOfFaces           = (numberOfFaces == 0) ? 1 : numberOfFaces;

        // si les valeurs sont les mêmes on ne recrée pas les variables
        if (numberOfMipmapLevels == _numberOfMipmapLevels && numberOfFaces == _numberOfFaces) {
            return;
        }

        _numberOfMipmapLevels   = (byte) numberOfMipmapLevels;
        _numberOfFaces          = (byte) numberOfFaces;
        _textureData            = new ByteBuffer[_numberOfMipmapLevels][_numberOfFaces];
        _imageSize              = new int[_numberOfMipmapLevels];
        _width                  = new short[_numberOfMipmapLevels];
        _height                 = new short[_numberOfMipmapLevels];

        // on contrôle le nombre de niveaux mipmap
        if (_numberOfMipmapLevels < 0) {
            throw new KTXFormatException("numberOfMipmapLevels can't be negative");
        }

        // on contrôle le nombre de faces
        if (_numberOfFaces < 0 || _numberOfFaces > 6) {
            throw new KTXFormatException("numberOfFaces must be between 0 and 6");
        }
    }

    /**
     * Défini le buffer contenant l'image correspondant au niveau mipmap 'mipmapLevel'
     * @param mipmapLevel
     * @param buffer
     */
    public void set(int mipmapLevel, ByteBuffer buffer) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        }

        _textureData[mipmapLevel][0]    = buffer;
        _imageSize[mipmapLevel]         = buffer.capacity();
    }

    /**
     * Défini le buffer contenant l'image correspondant au niveau mipmap 'mipmapLevel' et à la face 'face'
     * @param mipmapLevel
     * @param face
     * @param buffer
     */
    public void set(int mipmapLevel, int face, ByteBuffer buffer) {
        if (mipmapLevel > _numberOfMipmapLevels - 1) {
            throw new ArrayIndexOutOfBoundsException("No mipmap level " + String.valueOf(mipmapLevel));
        } else if (face > _numberOfFaces - 1) {
            throw new ArrayIndexOutOfBoundsException("No face " + String.valueOf(mipmapLevel));
        }

        _textureData[mipmapLevel][face] = buffer;
        _imageSize[mipmapLevel]         = buffer.capacity();
    }

    /**
     * Défini les dimensions de l'image d'un niveau mipmap
     * @param mipmapLevel
     * @param width
     * @param height
     */
    public void setDimensions(int mipmapLevel, int width, int height) {
        _width[mipmapLevel]     = (short) width;
        _height[mipmapLevel]    = (short) height;
    }

    /**
     * Classe qui permet de lire les données des textures contenues dans un fichier KTX
     */
    public static class Reader extends KTXTextureData {
        /**
         * Lit les données de texture du fichier
         * @param in le pointeur doit être placé au début des données
         * @param ktxHeader headers du fichier
         * @throws IOException
         */
        protected Reader(InputStream in, KTXHeader ktxHeader) throws IOException {
            int numberOfMipmapLevels    = ktxHeader.getNumberOfMipmapLevels();
            _textureData                = new ByteBuffer[ktxHeader.getNumberOfMipmapLevels()][ktxHeader.getNumberOfFaces()];
            _imageSize                  = new int[numberOfMipmapLevels];
            _width                      = new short[numberOfMipmapLevels];
            _height                     = new short[numberOfMipmapLevels];
            read(in, ktxHeader);
        }

        /**
         * Lit les données de texture du fichier
         * @param in le pointeur doit être placé au début des données
         * @param ktxHeader headers du fichier
         * @throws IOException
         */
        protected void read(InputStream in, KTXHeader ktxHeader) throws IOException {
            BufferedInputStream bufferedIn  = new BufferedInputStream(in);
            _numberOfMipmapLevels           = (byte) ktxHeader.getNumberOfMipmapLevels();
            _numberOfFaces                  = (byte) ktxHeader.getNumberOfFaces();
            _width[0]                       = (short) ktxHeader.getPixelWidth();
            _height[0]                      = (short) ktxHeader.getPixelHeight();
            long bytesRead                  = 0;
            ByteBuffer bufferBytesPerFace   = ByteBuffer.allocate(4);
            ByteOrder byteOrder             = ktxHeader.getByteOrder();
            byte[] faceData;
            byte mipPadding;
            byte cubePadding;
            byte i;

            for (byte mipmapLevel = 0; mipmapLevel < _numberOfMipmapLevels; mipmapLevel++) {
                // on récupère la taille de chaque face en prenant en compte l'ordre de lecture récupéré dans les headers
                _imageSize[mipmapLevel] = KTXUtil.readInt(bufferedIn, bufferBytesPerFace, byteOrder);
                faceData                = new byte[_imageSize[mipmapLevel]];
                _width[mipmapLevel]     = TextureUtils.getDimensionForMipmapLevel(mipmapLevel, _width[0]);
                _height[mipmapLevel]    = TextureUtils.getDimensionForMipmapLevel(mipmapLevel, _height[0]);

                for (byte face = 0; face < _numberOfFaces; face++) {
                    // on crée le ByteBuffer sans oublier de redéfinir son ordre à celui indique dans les headers
                    _textureData[mipmapLevel][face] = ByteBuffer.allocateDirect(_imageSize[mipmapLevel]);
                    bufferedIn.read(faceData);
                    _textureData[mipmapLevel][face].put(faceData);
                    _textureData[mipmapLevel][face].position(0);
                    _textureData[mipmapLevel][face].order(ktxHeader.getByteOrder());

                    bytesRead += _imageSize[mipmapLevel];

                    // cube padding
                    cubePadding = KTXUtil.align4(bytesRead);
                    bytesRead  += cubePadding;

                    for (i = 0; i < cubePadding; i++) {
                        bufferedIn.read();
                    }
                }                

                // mipmap padding
                mipPadding  = KTXUtil.align4(bytesRead);
                bytesRead  += mipPadding;

                for (i = 0; i < mipPadding; i++) {
                    bufferedIn.read();
                }
            }
        }
    }

    /**
     * Classe qui permet d'écrire les données des textures selon le format KTX dans un flux
     */
    public static class Writer extends KTXTextureData {
        KTXHeader _headers;

        /**
         * Initialise les données des textures
         * @param numberOfMipmapLevels  nombre de niveaux mipmap
         * @param numberOfFaces         nombre de faces par niveau mipmap
         * @throws KTXFormatException 
         */
        protected Writer(KTXHeader headers, int numberOfMipmapLevels, int numberOfFaces) throws KTXFormatException {
            _headers = headers;
            setNumberOfMipmapLevelsAndFaces(numberOfMipmapLevels, numberOfFaces);
        }

        /**
         * Contrôle l'integrité des données qui vont être insérées
         * @return
         * @throws KTXFormatException
         */
        protected void check() throws KTXFormatException {
            for (byte mipmapLevel = 0; mipmapLevel < _numberOfMipmapLevels; mipmapLevel++) {
                // on vérifie que des données existent
                if (null == _textureData[mipmapLevel] || 0 == _imageSize[mipmapLevel]) {
                    throw new KTXFormatException("No data defined for mipmap level " + String.valueOf(mipmapLevel));
                }

                //
                for (byte face = 0; face < _numberOfFaces; face++) {
                    // on vérifie que des données existent
                    if (null == _textureData[mipmapLevel][face]) {
                        throw new KTXFormatException("No data defined for face " + String.valueOf(face) + " of mipmap level " + String.valueOf(mipmapLevel));
                    }
                }
            }

            if (_headers.getNumberOfFaces() != _textureData[0].length) {
                throw new KTXFormatException(String.valueOf(_headers.getNumberOfFaces()) + " faces defined in headers, but " + String.valueOf(_textureData[0].length) + " have texture data");
            }

            if (_headers.getNumberOfMipmapLevels() != _textureData.length) {
                throw new KTXFormatException(String.valueOf(_headers.getNumberOfMipmapLevels()) + " mipmap levels defined in headers, but " + String.valueOf(_textureData.length) + " have texture data");
            }
        }

        /**
         * Écrit les données des textures dans un flux
         * @param out
         * @throws IOException
         * @throws KTXFormatException 
         */
        public void write(OutputStream out) throws IOException, KTXFormatException {
            check();

            ByteBuffer bufferBytesPerFace   = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            int bytesWrite                  = 0;
            byte[] bytes;
            int cubePadding;
            int mipPadding;
            byte i;

            for (byte mipmapLevel = 0; mipmapLevel < _numberOfMipmapLevels; mipmapLevel++) {
                _imageSize[mipmapLevel] = _textureData[mipmapLevel][0].capacity();
                bytes                   = new byte[_imageSize[mipmapLevel]];

                // on écrit la taille des données de chaque face
                bufferBytesPerFace.position(0);
                bufferBytesPerFace.asIntBuffer().put(_imageSize[mipmapLevel]);
                out.write(bufferBytesPerFace.array());

                for (byte face = 0; face < _numberOfFaces; face++) {
                    // on écrit les données de la face
                    _textureData[mipmapLevel][face].order(ByteOrder.nativeOrder());
                    _textureData[mipmapLevel][face].position(0);
                    _textureData[mipmapLevel][face].get(bytes);
                    out.write(bytes);
                    bytesWrite += _imageSize[mipmapLevel];

                    // cube padding
                    cubePadding = KTXUtil.align4(bytesWrite);
                    bytesWrite += cubePadding;

                    for (i = 0; i < cubePadding; i++) {
                        out.write(0);
                    }
                }

                // mipmap padding
                mipPadding  = KTXUtil.align4(bytesWrite);
                bytesWrite += mipPadding;
                
                for (i = 0; i < mipPadding; i++) {
                    out.write(0);
                }
            }
        }
    }

}
