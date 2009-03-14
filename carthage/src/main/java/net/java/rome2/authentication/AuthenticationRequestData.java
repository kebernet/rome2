/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kebernet
 */
public class AuthenticationRequestData {

    private Map<String, String> headers = new HashMap<String,String>();

    public Map<String, String> getHeaders(){
        return this.headers;
    }

    public void addHeader(String header, String value){
        this.headers.put(header, value);
    }
    

}
