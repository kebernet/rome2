package net.java.rome2.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A helper class that parses Dates out of Strings with date time in RFC822 and W3CDateTime formats plus the variants
 * Atom (0.3) and RSS (0.9, 0.91, 0.92, 0.93, 0.94, 1.0 and 2.0) specificators added to those formats.
 * <p/>
 * It uses the JDK java.text.SimpleDateFormat class attemtping the parse using a mask for each one of the possible
 * formats.
 * <p/>
 *
 * @author Alejandro Abdelnur
 */
public class DateUtils {

    // order is like this because the SimpleDateFormat.parse does not fail with exception
    // if it can parse a valid date out of a substring of the full string given the mask
    // so we have to check the most complete format first, then it fails with exception
    private static final String[] RFC822_MASKS =
            {"EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm z", "dd MMM yy HH:mm:ss z", "dd MMM yy HH:mm z"};


    // order is like this because the SimpleDateFormat.parse does not fail with exception
    // if it can parse a valid date out of a substring of the full string given the mask
    // so we have to check the most complete format first, then it fails with exception
    private static final String[] W3CDATETIME_MASKS =
            {"yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mmz",   // together with logic in the parseW3CDateTime they
             "yyyy-MM'T'HH:mmz",      // handle W3C dates without time forcing them to be GMT
             "yyyy'T'HH:mmz"          //
            };

    private String[] additionalMasks;

    public DateUtils() {
        this(null);
    }

    public DateUtils(String[] additionalMasks) {
        this.additionalMasks = additionalMasks;
    }

    /**
     * Parses a Date out of a string using an array of masks.
     * <p/>
     * It uses the masks in order until one of them succedes or all fail.
     * <p/>
     *
     * @param masks array of masks to use for parsing the string
     * @param sDate string to parse for a date.
     *
     * @return the Date represented by the given string using one of the given masks. It returns <b>null</b> if it was
     *         not possible to parse the the string with any of the masks.
     */
    private Date parseUsingMask(String[] masks, String sDate) {
        sDate = (sDate != null) ? sDate.trim() : null;
        ParsePosition pp;
        Date d = null;
        for (int i = 0; d == null && i < masks.length; i++) {
            DateFormat df = new SimpleDateFormat(masks[i], Locale.US);
            //df.setLenient(false);
            df.setLenient(true);
            try {
                pp = new ParsePosition(0);
                d = df.parse(sDate, pp);
                if (pp.getIndex() != sDate.length()) {
                    d = null;
                }
                //System.out.println("pp["+pp.getIndex()+"] s["+sDate+" m["+masks[i]+"] d["+d+"]");
            }
            catch (Exception ex1) {
                //System.out.println("s: "+sDate+" m: "+masks[i]+" d: "+null);
            }
        }
        return d;
    }

    /**
     * Parses a Date using the given additional masks.
     *
     * @param sDate
     */
    public Date parseMasks(String sDate) {
        return parseUsingMask(additionalMasks, sDate);
    }

    /**
     * Parses a Date out of a String with a date in RFC822 format.
     * <p/>
     * It parsers the following formats: <ul> <li>"EEE, dd MMM yyyy HH:mm:ss z"</li> <li>"EEE, dd MMM yyyy HH:mm z"</li>
     * <li>"EEE, dd MMM yy HH:mm:ss z"</li> <li>"EEE, dd MMM yy HH:mm z"</li> <li>"dd MMM yyyy HH:mm:ss z"</li> <li>"dd
     * MMM yyyy HH:mm z"</li> <li>"dd MMM yy HH:mm:ss z"</li> <li>"dd MMM yy HH:mm z"</li> </ul>
     * <p/>
     * Refer to the java.text.SimpleDateFormat javadocs for details on the format of each element.
     * <p/>
     *
     * @param sDate string to parse for a date.
     *
     * @return the Date represented by the given RFC822 string. It returns <b>null</b> if it was not possible to parse
     *         the given string into a Date.
     */
    public Date parseRFC822(String sDate) {
        int utIndex = sDate.indexOf(" UT");
        if (utIndex > -1) {
            String pre = sDate.substring(0, utIndex);
            String post = sDate.substring(utIndex + 3);
            sDate = pre + " GMT" + post;
        }
        return parseUsingMask(RFC822_MASKS, sDate);
    }


    /**
     * Parses a Date out of a String with a date in W3C date-time format.
     * <p/>
     * It parsers the following formats: <ul> <li>"yyyy-MM-dd'T'HH:mm:ssz"</li> <li>"yyyy-MM-dd'T'HH:mmz"</li>
     * <li>"yyyy-MM-dd"</li> <li>"yyyy-MM"</li> <li>"yyyy"</li> </ul>
     * <p/>
     * Refer to the java.text.SimpleDateFormat javadocs for details on the format of each element.
     * <p/>
     *
     * @param sDate string to parse for a date.
     *
     * @return the Date represented by the given W3C date-time string. It returns <b>null</b> if it was not possible to
     *         parse the given string into a Date.
     */
    public Date parseW3CDateTime(String sDate) {
        // if sDate has time on it, it injects 'GTM' before de TZ displacement to
        // allow the SimpleDateFormat parser to parse it properly
        int tIndex = sDate.indexOf("T");
        if (tIndex > -1) {
            if (sDate.endsWith("Z")) {
                sDate = sDate.substring(0, sDate.length() - 1) + "+00:00";
            }
            int tzdIndex = sDate.indexOf("+", tIndex);
            if (tzdIndex == -1) {
                tzdIndex = sDate.indexOf("-", tIndex);
            }
            if (tzdIndex > -1) {
                String pre = sDate.substring(0, tzdIndex);
                int secFraction = pre.indexOf(",");
                if (secFraction > -1) {
                    pre = pre.substring(0, secFraction);
                }
                String post = sDate.substring(tzdIndex);
                sDate = pre + "GMT" + post;
            }
        }
        else {
            sDate += "T00:00GMT";
        }
        return parseUsingMask(W3CDATETIME_MASKS, sDate);
    }


    /**
     * Parses a Date out of a String with a date in W3C date-time format or in a RFC822 format.
     * <p/>
     *
     * @param sDate string to parse for a date.
     *
     * @return the Date represented by the given W3C date-time string. It returns <b>null</b> if it was not possible to
     *         parse the given string into a Date.
     */
    public Date parseDate(String sDate) {
        Date d = parseW3CDateTime(sDate);
        if (d == null) {
            d = parseRFC822(sDate);
            if (d == null) {
                if (additionalMasks != null && additionalMasks.length > 0) {
                    d = parseUsingMask(additionalMasks, sDate);
                }
            }
        }
        return d;
    }

    /**
     * create a RFC822 representation of a date.
     * <p/>
     * Refer to the java.text.SimpleDateFormat javadocs for details on the format of each element.
     * <p/>
     *
     * @param date Date to parse
     *
     * @return the RFC822 represented by the given Date It returns <b>null</b> if it was not possible to parse the
     *         date.
     */
    public String formatRFC822(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormater.format(date);
    }

    /**
     * create a W3C Date Time representation of a date.
     * <p/>
     * Refer to the java.text.SimpleDateFormat javadocs for details on the format of each element.
     * <p/>
     *
     * @param date Date to parse
     *
     * @return the W3C Date Time represented by the given Date It returns <b>null</b> if it was not possible to parse
     *         the date.
     */
    public String formatW3CDateTime(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormater.format(date);
    }

}
