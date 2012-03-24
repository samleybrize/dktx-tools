package com.numericalactivity.dktxtools.ktx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe qui permet de lire un fichier KTX
 */
public class KTXReader {
    private KTXHeader _headers;
    private KTXMetadata _metas;
    private KTXTextureData _textureData;

    /**
     * Récupère et parse les données du fichier KTX
     * @param in flux pointant sur le fichier KTX. Le pointeur doit être placé au début du fichier
     * @throws IOException
     * @throws KTXFormatException
     */
    public KTXReader(InputStream in) throws IOException, KTXFormatException {
        // on crée un flux bufferisé à partir du flux passé en entrée
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in);
        }

        _headers        = new KTXHeader.Reader((BufferedInputStream) in);
        _metas          = new KTXMetadata.Reader((BufferedInputStream) in, _headers);
        _textureData    = new KTXTextureData.Reader((BufferedInputStream) in, _headers);
        in.close();
    }

    /**
     * Récupère et parse les données du fichier KTX
     * @param in flux pointant sur le fichier KTX. Le pointeur doit être placé au début du fichier
     * @param loadMetadatas indique si les métadonnées doivent être chargées
     * @throws IOException
     * @throws KTXFormatException
     */
    public KTXReader(InputStream in, boolean loadMetadatas) throws IOException, KTXFormatException {
        // on crée un flux bufferisé à partir du flux passé en entrée
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in);
        }

        _headers = new KTXHeader.Reader((BufferedInputStream) in);

        if (loadMetadatas) {
            // on charge les métadonnées
            _metas = new KTXMetadata.Reader((BufferedInputStream) in, _headers);
        } else {
            // si on ne charge pas les métadonnées, il faut faire avancer le pointeur de l'inputstream
            in.skip(_headers.getBytesOfKeyValueData());
        }

        _textureData = new KTXTextureData.Reader((BufferedInputStream) in, _headers);
        in.close();
    }

    /**
     * Retourne les headers
     * @return
     */
    public KTXHeader getHeaders() {
        return _headers;
    }

    /**
     * Retourne les métadonnées
     * @return
     */
    public KTXMetadata getMetadata() {
        return _metas;
    }

    /**
     * Retourne les données de texture
     * @return
     */
    public KTXTextureData getTextureData() {
        return _textureData;
    }

    /**
     * Retourne true si la texture est compressée
     * @return
     */
    public boolean isCompressed() {
        return 0 == _headers.getGLFormat();
    }
}
