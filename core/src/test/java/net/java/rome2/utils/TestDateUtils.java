package net.java.rome2.utils;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author tucu
 */
public class TestDateUtils extends TestCase {

    public void testParse() {
        DateUtils dateUtils = new DateUtils();

        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));

        // four-digit year
        String sDate = "Tue, 19 Jul 2005 23:00:51 GMT";
        cal.setTime(dateUtils.parseRFC822(sDate));

        assertEquals(2005, cal.get(Calendar.YEAR));
        assertEquals(6, cal.get(Calendar.MONTH)); // month is zero-indexed
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(Calendar.MINUTE));
        assertEquals(51, cal.get(Calendar.SECOND));

        // two-digit year
        sDate = "Tue, 19 Jul 05 23:00:51 GMT";
        cal.setTime(dateUtils.parseRFC822(sDate));

        assertEquals(2005, cal.get(Calendar.YEAR));
        assertEquals(6, cal.get(Calendar.MONTH)); // month is zero-indexed
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(Calendar.MINUTE));
        assertEquals(51, cal.get(Calendar.SECOND));

        // four-digit year
        sDate = "Tue, 19 Jul 2005 23:00:51 UT";
        cal.setTime(dateUtils.parseRFC822(sDate));

        assertEquals(2005, cal.get(Calendar.YEAR));
        assertEquals(6, cal.get(Calendar.MONTH)); // month is zero-indexed
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(Calendar.MINUTE));
        assertEquals(51, cal.get(Calendar.SECOND));

        // two-digit year
        sDate = "Tue, 19 Jul 05 23:00:51 UT";
        cal.setTime(dateUtils.parseRFC822(sDate));

        assertEquals(2005, cal.get(Calendar.YEAR));
        assertEquals(6, cal.get(Calendar.MONTH)); // month is zero-indexed
        assertEquals(19, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(Calendar.MINUTE));
        assertEquals(51, cal.get(Calendar.SECOND));

        //RFC822
        sDate = "Tue, 19 Jul 2005 23:00:51 GMT";
        assertNotNull(dateUtils.parseDate(sDate));

        //RFC822
        sDate = "Tue, 19 Jul 05 23:00:51 GMT";
        assertNotNull(dateUtils.parseDate(sDate));

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        Date expectedDate = c.getTime();

        //W3C
        sDate = "2000-01-01T00:00:00Z";
        assertEquals(expectedDate.getTime() / 1000, dateUtils.parseDate(sDate).getTime() / 1000);

        //W3C
        sDate = "2000-01-01T00:00Z";
        assertEquals(expectedDate.getTime() / 1000, dateUtils.parseDate(sDate).getTime() / 1000);

        //W3C
        sDate = "2000-01-01";
        assertEquals(expectedDate.getTime() / 1000, dateUtils.parseDate(sDate).getTime() / 1000);

        //W3C
        sDate = "2000-01";
        assertEquals(expectedDate.getTime() / 1000, dateUtils.parseDate(sDate).getTime() / 1000);

        //W3C
        sDate = "2000";
        assertEquals(expectedDate.getTime() / 1000, dateUtils.parseDate(sDate).getTime() / 1000);

        //INVALID
        sDate = "X20:10 2000-10-10";
        assertNull(dateUtils.parseDate(sDate));

    }


    public void testParseExtra() {
        DateUtils dateUtils = new DateUtils(new String[]{"HH:mm yyyy/MM/dd"});

        //EXTRA
        String sDate = "18:10 2000/10/10";
        assertNotNull(dateUtils.parseDate(sDate));


    }
}
