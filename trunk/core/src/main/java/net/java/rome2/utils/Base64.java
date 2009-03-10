package net.java.rome2.utils;

/**
 * Base64 provides encoding and decoding methods for Strings and byte arrays.
 */
public class Base64 {

    /**
     * Encodes a String into a base 64 String. The resulting encoding is warpped at 76 bytes.
     * <p/>
     *
     * @param s String to encode.
     *
     * @return encoded string.
     */
    public static String encodeString(String s) {
        return encodeBytes(s.getBytes(), true);
    }

    /**
     * Decodes a base 64 String into a String.
     * <p/>
     *
     * @param s String to decode.
     *
     * @return decoded string.
     *
     * @throws IllegalArgumentException thrown if the given byte array was not valid Base64 encoding.
     */
    public static String decodeString(String s) throws IllegalArgumentException {
        return new String(decodeBytes(s));
    }

    /**
     * Encodes a byte array into a base 64 String.
     *
     * @param b byte array to base 64 encode.
     * @param wrap indicates if the encoded string should be chunked at 76 bytes
     *
     * @return encoded string.
     */
    public static String encodeBytes(byte[] b, boolean wrap) {
        String s = new String(encode(b));
        if (wrap) {
            int fullBlocks = s.length() / 76;
            int leftover = s.length() % 76;
            StringBuffer sb = new StringBuffer(s.length() + fullBlocks + 1);
            for (int i = 0; i < fullBlocks; i++) {
                sb.append(s.subSequence(i * 76, (i + 1) * 76));
                sb.append("\n");
            }
            sb.append(s.subSequence(fullBlocks * 76, fullBlocks * 76 + leftover));
            sb.append("\n");
            s = sb.toString();
        }
        return s;
    }

    /**
     * Decodes a base 64 String into a byte array.
     * <p/>
     *
     * @param s String to decode.
     *
     * @return decoded byte array.
     *
     * @throws IllegalArgumentException thrown if the given byte array was not valid Base64 encoding.
     */
    public static byte[] decodeBytes(String s) {
        s = s.replaceAll("\n", "");
        s = s.replaceAll("\r", "");
        s = s.replaceAll(" ", "");
        byte[] sBytes = s.getBytes();
        return decode(sBytes);
    }

    private static final byte[] ALPHASET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".getBytes();

    private static final int I6O2 = 255 - 3;
    private static final int O6I2 = 3;
    private static final int I4O4 = 255 - 15;
    private static final int O4I4 = 15;
    private static final int I2O6 = 255 - 63;
    private static final int O2I6 = 63;

    /**
     * Encodes a byte array into a base 64 byte array.
     * <p/>
     *
     * @param dData byte array to encode.
     *
     * @return encoded byte array.
     */
    public static byte[] encode(byte[] dData) {
        if (dData == null) {
            throw new IllegalArgumentException("Cannot encode NULL");
        }
        byte[] eData = new byte[((dData.length + 2) / 3) * 4];

        int eIndex = 0;
        for (int i = 0; i < dData.length; i += 3) {
            int d1;
            int d2 = 0;
            int d3 = 0;
            int e1;
            int e2;
            int e3;
            int e4;
            int pad = 0;

            d1 = dData[i];
            if ((i + 1) < dData.length) {
                d2 = dData[i + 1];
                if ((i + 2) < dData.length) {
                    d3 = dData[i + 2];
                }
                else {
                    pad = 1;
                }
            }
            else {
                pad = 2;
            }

            e1 = ALPHASET[(d1 & I6O2) >> 2];
            e2 = ALPHASET[(d1 & O6I2) << 4 | (d2 & I4O4) >> 4];
            e3 = ALPHASET[(d2 & O4I4) << 2 | (d3 & I2O6) >> 6];
            e4 = ALPHASET[(d3 & O2I6)];

            eData[eIndex++] = (byte) e1;
            eData[eIndex++] = (byte) e2;
            eData[eIndex++] = (pad < 2) ? (byte) e3 : (byte) '=';
            eData[eIndex++] = (pad < 1) ? (byte) e4 : (byte) '=';

        }
        return eData;
    }

    private final static int[] CODES = new int[256];

    static {
        for (int i = 0; i < CODES.length; i++) {
            CODES[i] = 64;
        }
        for (int i = 0; i < ALPHASET.length; i++) {
            CODES[ALPHASET[i]] = i;
        }
    }

    /**
     * Decodes a com.sun.syndication.io.impl.Base64 byte array.
     * <p/>
     *
     * @param eData byte array to decode.
     *
     * @return decoded byte array.
     *
     * @throws IllegalArgumentException thrown if the given byte array was not Base64 encoding.
     */
    public static byte[] decode(byte[] eData) {
        if (eData == null) {
            throw new IllegalArgumentException("Cannot decode NULL");
        }
        byte[] cleanEData = (byte[]) eData.clone();
        int cleanELength = 0;
        for (int i = 0; i < eData.length; i++) {
            if (eData[i] < 256 && CODES[eData[i]] < 64) {
                cleanEData[cleanELength++] = eData[i];
            }
        }

        int dLength = (cleanELength / 4) * 3;
        switch (cleanELength % 4) {
            case 3:
                dLength += 2;
                break;
            case 2:
                dLength++;
                break;
        }

        byte[] dData = new byte[dLength];
        int dIndex = 0;
        for (int i = 0; i < eData.length; i += 4) {
            if ((i + 3) > eData.length) {
                throw new IllegalArgumentException("byte array is not a valid Base64 encoding");
            }
            int e1 = CODES[cleanEData[i]];
            int e2 = CODES[cleanEData[i + 1]];
            int e3 = CODES[cleanEData[i + 2]];
            int e4 = CODES[cleanEData[i + 3]];
            dData[dIndex++] = (byte) ((e1 << 2) | (e2 >> 4));
            if (dIndex < dData.length) {
                dData[dIndex++] = (byte) ((e2 << 4) | (e3 >> 2));
            }
            if (dIndex < dData.length) {
                dData[dIndex++] = (byte) ((e3 << 6) | (e4));
            }
        }
        return dData;
    }

}
