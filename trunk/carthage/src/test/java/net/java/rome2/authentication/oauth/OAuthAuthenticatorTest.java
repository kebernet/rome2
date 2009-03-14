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
public class OAuthAuthenticatorTest {

    public OAuthAuthenticatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGoogleGetToken() throws Exception {
        URI requestTokenUri = new URI("https://www.google.com/accounts/OAuthGetRequestToken");
        OAuthProvider provider = new OAuthProvider("rome.dev.java.net", null, null);

        OAuthAuthenticator auth = new OAuthAuthenticator( provider, "http://www.google.com/calendar/feeds", requestTokenUri, null);
    }

}