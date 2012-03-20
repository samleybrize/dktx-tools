package com.numericalactivity.dktxtools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {
    protected static MessageDigest _digestMd5;

    /**
     * Retourne la somme de contrôle md5 d'un fichier
     * @param file
     * @return
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    public static byte[] md5Checksum(String file) throws NoSuchAlgorithmException, IOException {
        return md5Checksum(new File(file));
    }

    /**
     * Retourne la somme de contrôle md5 d'un fichier
     * @param file
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
    public static byte[] md5Checksum(File file) throws NoSuchAlgorithmException, IOException {
        if (null == _digestMd5) {
            _digestMd5 = MessageDigest.getInstance("md5");
        }

        FileInputStream in  = new FileInputStream(file);
        byte[] data         = new byte[(int) file.length()];
        in.read(data);
        in.close();
        return _digestMd5.digest(data);
    }

    /**
     * Retourne la somme de contrôle md5 des données contenues dans un ByteBuffer
     * @param buffer
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
    public static byte[] md5Checksum(ByteBuffer buffer) throws NoSuchAlgorithmException {
        if (null == _digestMd5) {
            _digestMd5 = MessageDigest.getInstance("md5");
        }
        
        byte[] data         = new byte[buffer.capacity()];
        buffer.get(data);
        return _digestMd5.digest(data);
    }

    /**
     * Retourne true si les deux fichiers sont égaux
     * @param file1
     * @param file2
     * @return
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    public static boolean isEqual(String file1, String file2) throws NoSuchAlgorithmException, IOException {
        return isEqual(new File(file1), new File(file2));
    }

    /**
     * Retourne true si les deux fichiers sont égaux
     * @param file1
     * @param file2
     * @return
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    public static boolean isEqual(File file1, File file2) throws NoSuchAlgorithmException, IOException {
        return MessageDigest.isEqual(md5Checksum(file1), md5Checksum(file2));
    }

    /**
     * Retourne true si les données contenues dans les deux ByteBuffer sont égales
     * @param buffer1
     * @param buffer2
     * @return
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    public static boolean isEqual(ByteBuffer buffer1, ByteBuffer buffer2) throws NoSuchAlgorithmException, IOException {
        return MessageDigest.isEqual(md5Checksum(buffer1), md5Checksum(buffer2));
    }
}
