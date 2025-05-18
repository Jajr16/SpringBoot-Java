package com.example.PruebaCRUD.Scraping;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLConfig {
    private static SSLContext sslContext = null;
    private static SSLContext firebaseSslContext = null;

    public static synchronized void configureSSL() {
        if (sslContext != null) return;
        try {
            // Configuración para IPN
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

            certStream.close();
            sslContext = sc;

            // Configuración adicional para Firebase
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            firebaseSslContext = SSLContext.getInstance("TLS");
            firebaseSslContext.init(null, trustAllCerts, null);

            System.out.println("Certificados configurados exitosamente");
        } catch (Exception e) {
            System.err.println("Error al configurar SSL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized void configurarParaIPN() {
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
    }

    public static synchronized void configurarParaFirebase() {
        if (firebaseSslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(firebaseSslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        }
    }
}