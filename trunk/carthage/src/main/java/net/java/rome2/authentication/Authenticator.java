/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

import java.io.Serializable;
import java.net.URI;

/**
 *
 * @author kebernet
 */
public interface Authenticator<T extends AuthenticationCallback> extends Serializable {

    public boolean isValid();

    public boolean validate(URI uri, T callback);

    public AuthenticationRequestData getRequestData();

    public void setResponseData(AuthenticationResponseData response);


}
