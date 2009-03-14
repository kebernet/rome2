/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication.oauth;

import java.net.URI;
import net.java.rome2.authentication.URICallback;

/**
 *
 * @author kebernet
 */
public class OAuthURLCallback extends URICallback{

    private URI loginUrl;
    private String token;
    @Override
    protected void setLoginUri(URI loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }



}
