/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

/**
 *
 * @author kebernet
 */
public abstract class UsernamePasswordCallback implements AuthenticationCallback {

    public abstract String getUsername();

    public abstract String getPassword();

}
