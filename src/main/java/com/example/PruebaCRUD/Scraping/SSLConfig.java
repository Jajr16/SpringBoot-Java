package com.example.PruebaCRUD.Scraping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

public class SSLConfig {
    private static final Logger logger = LoggerFactory.getLogger(SSLConfig.class);
    private static boolean isConfigured = false;

    public static void configureSSL() throws Exception {
        // Evitar configuración múltiple
        if (isConfigured) {
            return;
        }

        logger.info("Iniciando configuración SSL...");

        try {
            // Obtener el certificado como recurso
            InputStream certStream = SSLConfig.class.getResourceAsStream("/certs/ipn.mx.crt");
            if (certStream == null) {
                logger.warn("No se pudo encontrar el certificado, usando configuración alternativa...");
                configurarSSLAlternativo();
                return;
            }

            // Crear y configurar el KeyStore
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            keyStore.setCertificateEntry("ipn_cert", cf.generateCertificate(certStream));

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Configurar verificador de hostname específico para IPN
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) ->
                    hostname.endsWith(".ipn.mx"));

            logger.info("Configuración SSL completada exitosamente");
            isConfigured = true;

        } catch (Exception e) {
            logger.error("Error en configuración SSL principal, usando configuración alternativa", e);
            configurarSSLAlternativo();
        }
    }

    private static void configurarSSLAlternativo() {
        try {
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

            logger.info("Configuración SSL alternativa completada");
            isConfigured = true;
        } catch (Exception e) {
            logger.error("Error fatal en configuración SSL alternativa", e);
            throw new RuntimeException("No se pudo configurar SSL", e);
        }
    }
}