package com.numericalactivity.dktxtools.nap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class NAPackageWriter {

    protected ZipOutputStream _zipOut;

    /**
     * Constructeur
     * @param file fichier à lire
     * @throws FileNotFoundException
     */
    public NAPackageWriter(File file) throws FileNotFoundException {
        _zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
    }

    /**
     * Constructeur
     * @param filename chemin vers le fichier à lire
     * @throws FileNotFoundException
     */
    public NAPackageWriter(String filename) throws FileNotFoundException {
        _zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
    }

    /**
     * Ferme le fichier
     * @throws IOException 
     */
    public void close() throws IOException {
        _zipOut.close();
    }

    /**
     * Ajoute un fichier au package
     * @param entryName nom du fichier à ajouter (relatif à la racine du package)
     * @param data données à insérer
     * @return
     * @throws IOException
     */
    public NAPackageWriter addEntry(String entryName, byte[] data) throws IOException {
        _zipOut.putNextEntry(new ZipEntry(entryName));
        _zipOut.write(data);
        _zipOut.closeEntry();
        return this;
    }

    /**
     * Ajoute un fichier au package
     * @param entryName nom du fichier à ajouter (relatif à la racine du package)
     * @param buffer données à insérer
     * @return
     * @throws IOException
     */
    public NAPackageWriter addEntry(String entryName, ByteBuffer buffer) throws IOException {
        byte[] data = new byte[buffer.capacity()];
        buffer.get(data);

        _zipOut.putNextEntry(new ZipEntry(entryName));
        _zipOut.write(data);
        _zipOut.closeEntry();
        return this;
    }

    /**
     * Ajoute un fichier au package
     * @param entryName nom du fichier à ajouter (relatif à la racine du package)
     * @param in flux contenant les données à insérer
     * @return
     * @throws IOException
     */
    public NAPackageWriter addEntry(String entryName, InputStream in) throws IOException {
        BufferedInputStream bufferedIn  = new BufferedInputStream(in);
        int available                   = bufferedIn.available();
        _zipOut.putNextEntry(new ZipEntry(entryName));
        byte[] data;

        while (available > 0) {
            data = new byte[available];
            bufferedIn.read(data);
            _zipOut.write(data);
            available = bufferedIn.available();
        }
        
        _zipOut.closeEntry();
        return this;
    }
}
