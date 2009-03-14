/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication.oauth;

import java.io.IOException;
import java.net.URI;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.rome2.authentication.AuthenticationRequestData;
import net.java.rome2.authentication.AuthenticationResponseData;
import net.java.rome2.authentication.Authenticator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author kebernet
 */
public class OAuthAuthenticator implements Authenticator<OAuthURLCallback> {
    
    private URI requestTokenUrl;
    private URI userAuthorizationUrl;
    private URI accessTokenUrl;
    private OAuthURLCallback callback;
    private Random random = new Random();
    private String domain;
    private OAuthProvider provider;
    
    public OAuthAuthenticator(OAuthProvider provider, String domain, URI requestTokenUrl, URI accessTokenUrl){
            this.requestTokenUrl = requestTokenUrl;
            this.accessTokenUrl = accessTokenUrl;
            this.domain = domain;
            this.provider = provider;
            this.getTokens();
    }

    public boolean isValid() {
        return callback.getToken() != null;
    }

    public boolean validate(OAuthURLCallback callback) {
        callback.setLoginUri(userAuthorizationUrl);
        this.callback = callback;
        return isValid();
    }

    public AuthenticationRequestData getRequestData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setResponseData(AuthenticationResponseData response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void getTokens(){
        try {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(this.requestTokenUrl.toString());
            method.setRequestHeader("Authorization", "OAuth");
            SignedParameters sign = new SignedParameters(provider.getKeyPair(requestTokenUrl), "POST", this.requestTokenUrl);
            method.addParameter("oauth_consumer_key", provider.getClientId());
            sign.addParameter("oauth_consumer_key", provider.getClientId());
            method.addParameter("oauth_signature_method", "RSA-SHA1");
            sign.addParameter("oauth_signature_method", "RSA-SHA1");
            long timestamp = System.currentTimeMillis() / 1000;
            method.addParameter("oauth_timestamp", Long.toString(timestamp));
            sign.addParameter("oauth_timestamp", Long.toString(timestamp));
            long nonce = random.nextLong();
            nonce = nonce < 0 ? -1 * nonce : nonce;
            method.addParameter("oauth_nonce", Long.toString(nonce));
            sign.addParameter("oauth_nonce", Long.toString(nonce));
            method.addParameter("oauth_version", "1.0");
            sign.addParameter("oauth_version", "1.0");
            method.addParameter("scope", this.domain);
            sign.addParameter("scope", this.domain);
            method.addParameter("oauth_signature", sign.toString());
            int code = client.executeMethod(method);
            System.out.println(code+"\n"+method.getResponseBodyAsString());
        } catch (IOException ex) {
            Logger.getLogger(OAuthAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }


}
