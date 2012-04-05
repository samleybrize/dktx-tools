package com.numericalactivity.dktxtools.ktx;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.numericalactivity.dktxtools.dds.DDSHeader;
import com.numericalactivity.dktxtools.dds.DDSReader;

// TODO tests u
public class KTXConvert {
    /**
     * Converti un fichier DDS en fichier KTX
     * @param ddsReader fichier DDS à convertir
     * @param out flux dans lequel sera écrit le fichier KTX
     * @throws KTXFormatException
     * @throws IOException
     */
    public static void convertDDS(DDSReader ddsReader, OutputStream out) throws KTXFormatException, IOException {
        // on crée un flux bufferisé à partir du flux passé en entrée
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out);
        }

        // on récupère les informations du fichier DDS
        DDSHeader ddsHeader             = ddsReader.getHeaders();
        byte numberOfMipmap             = (byte) ddsHeader.getMipmapCount();
        boolean mipmapped               = numberOfMipmap > 1;
        boolean isCubemap               = ddsHeader.hasCaps2(DDSHeader.DDSCAPS2_CUBEMAP);
        short width                     = (short) ddsHeader.getWidth();
        short height                    = (short) ddsHeader.getHeight();
        int glFormat                    = ddsReader.getOpenglFormat();
        ByteBuffer[][] buffers          = ddsReader.getTextureData().getAll();
        byte numberOfFaces              = (byte) ((buffers.length > 0) ? buffers[0].length : 0);

        // on crée le writer KTX
        KTXWriter ktxWriter             = KTXWriter.getNew(mipmapped, isCubemap, width, height);
        KTXTextureData ktxTextureData   = ktxWriter.getTextureData();

        if (ddsReader.isCompressed()) {
            ktxWriter.setCompressedFormat(glFormat);
        } else {
            ktxWriter.setUncompressedFormat(glFormat);
        }

        // on défini les données de textures
        byte mipmapLevel        = 0;
        byte face               = 0;

        for (mipmapLevel = 0; mipmapLevel < numberOfMipmap; mipmapLevel++) {
            for (face = 0; face < numberOfFaces; face++) {
                ktxTextureData.set(mipmapLevel, face, buffers[mipmapLevel][face]);
            }
        }

        // écriture du fichier KTX
        ktxWriter.write(out);
        ktxWriter.recycle();
    }
}
