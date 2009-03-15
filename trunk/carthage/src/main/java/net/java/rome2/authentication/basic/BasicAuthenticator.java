/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication.basic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.rome2.authentication.AuthenticationRequestData;
import net.java.rome2.authentication.AuthenticationResponseData;
import net.java.rome2.authentication.Authenticator;
import net.java.rome2.authentication.UsernamePasswordCallback;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.HeadMethod;

/**
 *
 * @author kebernet
 */
public class BasicAuthenticator implements Authenticator<UsernamePasswordCallback> {

    private boolean validated = false;
    private AuthenticationRequestData data = new AuthenticationRequestData();
    public boolean isValid() {
        return this.validated; 
    }

    public boolean validate(URI uri, UsernamePasswordCallback callback) {
        try {
            StringBuilder sb = new StringBuilder().append(callback.getUsername()).append(":").append(callback.getPassword());
            String token = new String(new Base64().encode(sb.toString().getBytes()), "UTF-8");
            this.data.addHeader("Authorization", "Basic "+token);
            this.validated = validate(uri);
            return this.validated;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BasicAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private boolean validate(URI uri){
        try {
            HttpClient client = new HttpClient();
            HeadMethod method = new HeadMethod(uri.toString());
            for (Entry<String, String> entry : this.data.getHeaders().entrySet()) {
                method.addRequestHeader(entry.getKey(), entry.getValue());
            }
            int code = client.executeMethod(method);
            return code == 200;
        } catch (IOException ex) {
            Logger.getLogger(BasicAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public AuthenticationRequestData getRequestData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setResponseData(AuthenticationResponseData response) {
        // Don't have to do anything here.
    }

}
