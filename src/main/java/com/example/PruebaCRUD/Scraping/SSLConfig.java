package com.example.PruebaCRUD.Scraping;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

public class SSLConfig {
    public static void configureSSL() {
        try {
            // Usar directamente la ruta del archivo, que sabemos que funciona
            FileInputStream fis = new FileInputStream("src/main/resources/certs/ipn.mx.crt");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            keyStore.setCertificateEntry("ipn_cert", cf.generateCertificate(fis));

            // Crear TrustManager que use el certificado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // Configurar SSLContext
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);

            // Establecer como contexto SSL predeterminado
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            fis.close();
            System.out.println("Certificado cargado exitosamente");

        } catch (Exception e) {
            System.err.println("Error al cargar el certificado: " + e.getMessage());
            e.printStackTrace();
            // No usar configuración de respaldo, para que sepamos si hay un problema
        }
    }
}