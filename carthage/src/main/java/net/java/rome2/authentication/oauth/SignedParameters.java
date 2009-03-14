/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author kebernet
 */
public class SignedParameters {

    private String method;
    private URI uri;
    private List<String> keys = new ArrayList<String>();;
    private List<String> values = new ArrayList<String>();
    private String privateKey;
    private KeyPair keyPair;
    public SignedParameters(KeyPair keyPair, String method, URI uri){
        this.keyPair = keyPair;
        this.method = method;
        this.uri = uri;
        String query = uri.getQuery();
        if(query != null && query.trim().length() > 0){
            String[] tokens = query.split("&");
            for(String token : tokens){
                String[] pair = token.split("=");
                this.addParameter(pair[0], pair[1]);
            }
        }
    }

    public void addParameter(String name, String value){
        this.keys.add(name);
        this.values.add(value);
    }
    
    @Override
    public String toString(){
        HashMap<Integer,String> indexesToKeys = new HashMap<Integer, String>();
        for(int i=0; i < keys.size(); i++){
            indexesToKeys.put( i, this.keys.get(i));
        }
        ArrayList<Entry<Integer,String>> indexes = new ArrayList<Entry<Integer,String>>();
        indexes.addAll( indexesToKeys.entrySet() );
        Entry<Integer,String>[] sorted = indexes.toArray(new Entry[ indexes.size()] );
        Arrays.sort(sorted, new Comparator<Entry<Integer,String>>(){

            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getValue().compareTo( o1.getValue() );
            }

        });
        StringBuilder sb = new StringBuilder()
                .append(method)
                .append("&")
                .append( this.uri.getScheme() )
                .append("//")
                .append( this.uri.getHost() )
                .append(":")
                .append( normalizePort(uri) )
                .append( uri.getPath() )
                .append("&");
        boolean first = true;
        for(Entry<Integer, String> key : sorted){
            try {
                if(!first){
                    sb = sb.append("&");
                }
                sb = sb.append(key.getValue()).append("=")
                        .append(URLEncoder.encode(this.values.get(key.getKey()), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SignedParameters.class.getName()).log(Level.SEVERE, "Unsupported encoding exception on UTF-8. WTH kind of JRE are you using?", ex);
                throw new RuntimeException(ex);
            }
        }
        return this.sign(sb.toString());
    }

    private static int normalizePort(URI uri){
        if( uri.getPort() != -1 ){
            return uri.getPort();
        } else if( uri.getScheme().equals("https:") ){
            return 443;
        } else if( uri.getScheme().equals("http:") ){
            return 80;
        }
        return 0;
    }

    private String sign(String input) {
        try {
            Signature instance = Signature.getInstance("SHA1withRSA");
            instance.initSign(keyPair.getPrivate());
            instance.update((input).getBytes());
            byte[] signature = instance.sign();
            Base64 codec = new Base64();
            return new String(codec.encode(signature), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SignedParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(SignedParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SignedParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SignedParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
