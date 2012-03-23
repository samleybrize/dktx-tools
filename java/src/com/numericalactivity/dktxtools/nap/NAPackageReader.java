package com.numericalactivity.dktxtools.nap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.numericalactivity.dktxtools.utils.BufferUtils;

public class NAPackageReader {

    protected ZipFile _zipFile;

    /**
     * Constructeur
     * @param file
     * @throws ZipException
     * @throws IOException
     */
    public NAPackageReader(File file) throws ZipException, IOException {
        _zipFile = new ZipFile(file);
    }

    /**
     * Constructeur
     * @param filename nom du fichier
     * @throws ZipException
     * @throws IOException
     */
    public NAPackageReader(String filename) throws ZipException, IOException {
        _zipFile = new ZipFile(filename);
    }

    /**
     * Retourne un InputStream pointant sur les données de l'entrée spécifiée par 'entryName'
     * @param entryName nom de l'entrée. Habituellement un chemin interne au fichier.
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(String entryName) throws IOException {
        return _zipFile.getInputStream(_zipFile.getEntry(entryName));
    }

    /**
     * Retourne les données correspondants à l'entrée spécifiée par 'entryName'
     * @param entryName nom de l'entrée. Habituellement un chemin interne au fichier.
     * @return
     * @throws IOException
     * @throws NAPackageException 
     */
    public ByteBuffer get(String entryName) throws IOException, NAPackageException {
        ZipEntry zipEntry   = _zipFile.getEntry(entryName);

        // on vérifie que l'entrée existe
        if (null == zipEntry) {
            throw new NAPackageException("Entry '" + entryName + "' does not exists");
        }

        // on vérifie la taille de l'entrée
        long size           = zipEntry.getSize();

        if (size > Integer.MAX_VALUE) {
            throw new NAPackageException("Too long data size for this entry");
        }

        // on récupère les données de l'entrée
        byte[] data         = new byte[(int) size];
        InputStream in      = _zipFile.getInputStream(zipEntry);
        in.read(data);
        return BufferUtils.getByteBuffer(data);
    }

    /**
     * Retourne la taille des données de l'entrée spécifiée par 'entryName'
     * @param entryName nom de l'entrée. Habituellement un chemin interne au fichier.
     * @return
     * @throws NAPackageException
     */
    public long getEntrySize(String entryName) throws NAPackageException {
        ZipEntry zipEntry   = _zipFile.getEntry(entryName);

        // on vérifie que l'entrée existe
        if (null == zipEntry) {
            throw new NAPackageException("Entry '" + entryName + "' does not exists");
        }

        return zipEntry.getSize();
    }
}
