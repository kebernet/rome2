package net.java.rome2.utils;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TestCloneUtils extends TestCase {

    public void testCloneNull() {
        try {
            CloneUtils.clone(null);
            fail();
        }
        catch (CloneNotSupportedException ex) {
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    public void testNotCloneable() {
        try {
            CloneUtils.clone(new TestCloneUtils());
            fail();
        }
        catch (CloneNotSupportedException ex) {
            assertTrue(true);
        }
        catch (IllegalArgumentException ex) {
            fail();
        }
    }

    public void testCloning() throws CloneNotSupportedException {
        Date s = new Date();
        Object c = CloneUtils.clone(s);
        assertEquals(s, c);
    }

    public void testImmutableClasses() throws CloneNotSupportedException {
        CloneUtils.clone("");
        CloneUtils.clone((byte) 1);
        CloneUtils.clone('a');
        CloneUtils.clone((short) 1);
        CloneUtils.clone(1);
        CloneUtils.clone((long) 1);
        CloneUtils.clone((float) 1.0);
        CloneUtils.clone(1.0);
        CloneUtils.clone(new BigDecimal(1.0));
        CloneUtils.clone(new BigInteger("1"));
        CloneUtils.clone(new AtomicInteger(1));
        CloneUtils.clone(new AtomicLong(1));
    }

}
