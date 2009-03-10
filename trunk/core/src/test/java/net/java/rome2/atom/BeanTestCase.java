package net.java.rome2.atom;

import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tucu
 */
public class BeanTestCase extends TestCase {

    protected static <T> boolean notNull(T t1, T t2) {
        if ((t1 == null && t2 != null) || (t1 != null && t2 == null)) {
            fail();
        }
        return (t1 != null);
    }

    protected static <T> void assertListEquals(List<T> l1, List<T> l2, Class assertingClass, Class assertClass) {
        if (notNull(l1, l2)) {
            assertEquals(l1.size(), l2.size());
            try {
                Method assertMethod = assertingClass.getMethod("assertEquals", assertClass, assertClass);
                for (int i = 0; i < l1.size(); i++) {
                    assertMethod.invoke(null, l1.get(i), l2.get(i));
                }
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void testNotNull() {
        if (notNull(null, null)) {
            fail();
        }
        if (!notNull("", "")) {
            fail();
        }
        try {
            notNull("", null);
            fail();
        }
        catch (Throwable ex) {
        }
        try {
            notNull(null, "");
            fail();
        }
        catch (Throwable ex) {
        }
    }

    public void testAssertListEquals() {
        List<String> l1 = new ArrayList<String>();
        List<String> l2 = new ArrayList<String>();
        assertListEquals(null, null, TestCase.class, String.class);
        assertListEquals(l1, l2, TestCase.class, String.class);
        try {
            assertListEquals(l1, null, TestCase.class, String.class);
            fail();
        }
        catch (Throwable ex) {
        }
        try {
            assertListEquals(null, l2, TestCase.class, String.class);
            fail();
        }
        catch (Throwable ex) {
        }
        l1.add("");
        try {
            assertListEquals(l1, l2, TestCase.class, String.class);
            fail();
        }
        catch (Throwable ex) {
        }
        l2.add("");
        assertListEquals(l1, l2, TestCase.class, String.class);
        l1.add("a");
        try {
            assertListEquals(l1, l2, TestCase.class, String.class);
            fail();
        }
        catch (Throwable ex) {
        }
    }
}
