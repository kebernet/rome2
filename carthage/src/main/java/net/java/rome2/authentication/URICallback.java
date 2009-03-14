/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

import java.net.URI;

/**
 *
 * @author kebernet
 */
public abstract class URICallback implements AuthenticationCallback {

    protected abstract void setLoginUri(URI loginUrl);

    public abstract String getToken();
}
