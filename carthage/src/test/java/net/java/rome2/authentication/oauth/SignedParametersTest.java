/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.java.rome2.authentication.oauth;

import java.net.URI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kebernet
 */
public class SignedParametersTest {

    public SignedParametersTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Just a scratch method to see if URI adds ? to the first param.
     * @throws java.lang.Exception
     */
    @Test
    public void testQueryDecode() throws Exception {
        URI uri = new URI("http://some.com/path/to/something?id=2&checkone=a&checkone=b&text=This%20is%20a%20test");
        System.out.println(uri.getQuery() );
        String[] tokens = uri.getQuery().split("&");
        for(String token : tokens){
            String[] pair = token.split("=");
            System.out.println("key:"+pair[0]);
            System.out.println("value:"+pair[1]);
        }
    }

}