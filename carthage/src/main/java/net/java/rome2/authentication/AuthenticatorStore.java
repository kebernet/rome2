/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public class AuthenticatorStore implements Serializable {


    private Map<URI, Authenticator> authenticators = new HashMap<URI, Authenticator>();

    public Authenticator findAuthenticator(URI uri){
        return authenticators.get(uri);
    }



}
