/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

import org.apache.commons.httpclient.HttpMethod;

/**
 *
 * @author kebernet
 */
public interface AuthenticatorProvider {


    public void init(Discovery store);

    Authenticator getAuthenticator(HttpMethod method);


}
