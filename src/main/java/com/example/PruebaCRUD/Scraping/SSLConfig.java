package com.example.PruebaCRUD.Scraping;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

public class SSLConfig {
    private static boolean isConfigured = false;

    public static void configureSSL() throws Exception {
        if (isConfigured) {
            return;
        }

        try {
            InputStream certStream = SSLConfig.class.getResourceAsStream("/certs/ipn.mx.crt");
            if (certStream == null) {
                // Si no encuentra el certificado, usa configuración alternativa
                configurarSSLAlternativo();
                return;
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
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> hostname.endsWith(".ipn.mx"));

            isConfigured = true;

        } catch (Exception e) {
            configurarSSLAlternativo();
        }
    }

    private static void configurarSSLAlternativo() throws Exception {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }}, new java.security.SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        isConfigured = true;
    }
}