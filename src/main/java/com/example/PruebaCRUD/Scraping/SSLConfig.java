package com.example.PruebaCRUD.Scraping;

import org.openqa.selenium.chrome.ChromeOptions;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

public class SSLConfig {
    public static void configureSSL() {
        try {
            // Obtener el certificado como recurso del classpath
            InputStream certStream = SSLConfig.class.getClassLoader().getResourceAsStream("certs/ipn.mx.crt");
            if (certStream == null) {
                throw new FileNotFoundException("No se pudo encontrar el certificado en el classpath");
            }

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            keyStore.setCertificateEntry("ipn_cert", cf.generateCertificate(certStream));

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            certStream.close();
            System.out.println("Certificado cargado exitosamente desde el classpath");

        } catch (Exception e) {
            System.err.println("Error al configurar SSL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}