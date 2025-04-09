package com.example.PruebaCRUD.CryptAES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Clase para cifrar texto
 */
public class AES {
    private static final String SECRET_KEY = "parangaricutimir"; // Llave secreta que ocuparemos para encriptar y desencriptar

    // Función que convierte la llave secreta al tipo SecretKet
    private static SecretKey getSecretKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    }

    // Función que regresa el texto encriptado
    public static String Encriptar(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Función para desencriptar texto
    public static String Desencriptar(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(decryptedBytes);
    }
}
