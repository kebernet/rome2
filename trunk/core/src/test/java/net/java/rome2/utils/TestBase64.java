package net.java.rome2.utils;

import junit.framework.TestCase;

public class TestBase64 extends TestCase {

    private void _testEncodeDecode(String s) {
        String encoded = Base64.encodeString(s);
        String decoded = Base64.decodeString(encoded);
        assertEquals(s, decoded);
    }

    public void testEncodeDecode() throws Exception {
        _testEncodeDecode("");
        _testEncodeDecode("A");
        _testEncodeDecode("AB");
        _testEncodeDecode("ABC");
        _testEncodeDecode("ABCD");
        _testEncodeDecode("ABCDE");
        _testEncodeDecode("&");
        _testEncodeDecode("a&");
        _testEncodeDecode("ab&");
        _testEncodeDecode("abc&");
        _testEncodeDecode("abcd&");

    }

    public void testDecodeWithEnters() {
        String s = "Hello World!";
        String encoded = Base64.encodeString(s);
        encoded = encoded.substring(0, 3) + "\n \r\n " + encoded.substring(3);
        String decoded = Base64.decodeString(encoded);
        assertEquals(s, decoded);
    }

    public void test76Wrapping() {
        String s = "0123456789012345678901234567890123456789012345678901234567890123456789";
        String encoded = Base64.encodeString(s);
        assertEquals(76, encoded.indexOf("\n"));
        String decoded = Base64.decodeString(encoded);
        assertEquals(s, decoded);
    }
}
