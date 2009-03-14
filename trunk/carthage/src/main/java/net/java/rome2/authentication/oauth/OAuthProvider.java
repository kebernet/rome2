/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.java.rome2.authentication.oauth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author kebernet
 */
public class OAuthProvider {
    private KeyStore keystore;
    private String clientId;
    private File keystoreFile;

    public OAuthProvider(String clientId, File keystoreFile, String keystoreType)
        throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException {
        this.clientId = clientId;
        keystoreType = (keystoreType == null) ? KeyStore.getDefaultType()
                                              : keystoreType;
        keystoreFile = (keystoreFile == null)
            ? new File(System.getProperty("user.home"), ".carthage/keystore")
            : keystoreFile;
        this.keystoreFile = keystoreFile;
        this.keystore = KeyStore.getInstance(keystoreType);

        if (!keystoreFile.exists()) {
            keystoreFile.getParentFile().mkdirs();
            keystoreFile.createNewFile();
            Logger.getLogger(OAuthProvider.class.getName())
                  .log(Level.INFO,
                "Creating new keystore file in: " +
                keystoreFile.getAbsolutePath());
            this.keystore.load(null, new char[0]);
            this.store();
        }

        this.keystore.load(new FileInputStream(keystoreFile), new char[0]);
    }

    private void store() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
         FileOutputStream fos = new FileOutputStream(keystoreFile);
         this.keystore.store(fos, new char[0]);
         fos.close();
    }

    public String getClientId() {
        return this.clientId;
    }

    public KeyPair getKeyPair(URI requestTokenUri) {
        try {
            String alias = requestTokenUri.getHost();
            Key key = keystore.getKey(alias, new char[0]);

            if (key instanceof PrivateKey) {
                // Get certificate of public key
                java.security.cert.Certificate cert = keystore.getCertificate(alias);

                // Get public key
                PublicKey publicKey = cert == null ? (PublicKey) keystore.getKey(alias+"-public", new char[0]) : cert.getPublicKey();

                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey) key);
            } 

            Logger.getLogger(OAuthProvider.class.getName())
                  .log(Level.SEVERE, "No key alias found for hostname " +
                alias+". Please run: \"keytool -genkey -alias "+alias+" -keyalg RSA -keysize 512 -keystore "+keystoreFile.getAbsolutePath()+"\" and provide an empty password. ");
            throw new RuntimeException("No key alias found for hostname " +
                alias);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(OAuthProvider.class.getName())
                  .log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(OAuthProvider.class.getName())
                  .log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(OAuthProvider.class.getName())
                  .log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
