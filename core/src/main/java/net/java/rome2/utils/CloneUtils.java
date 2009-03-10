package net.java.rome2.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class that allows cloning an object without having to do an explicit cast. <p> The <tt>clone()</tt> method of
 * the <tt>Object</tt> class is protected. Subclasses supporting cloning should implement the <tt>Cloneable</tt>
 * interface and override the <tt>clone()</tt> method making it public. The {@link #clone} method of this utility class
 * checks if the <tt>clone</tt> method of the given object is public and uses reflection to invoke it. </p>
 * <p/>
 * For the following immutable classes the {@link #clone(Object)} method returns the given value:
 * <p/>
 * <tt> <ul> <li>String</li> <li>Byte</li> <li>Character</li> <li>Short</li> <li>Integer</li> <li>Long</li>
 * <li>Double</li> <li>BigDecimal</li> <li>BigInteger</li> <li>AtomicInteger</li> <li>AtomicLong</li> </ul> </tt>
 */
public class CloneUtils {
    private static final Set<Class> IMMUTABLE_CLASSES = new HashSet<Class>();

    static {
        IMMUTABLE_CLASSES.add(String.class);
        IMMUTABLE_CLASSES.add(Byte.class);
        IMMUTABLE_CLASSES.add(Character.class);
        IMMUTABLE_CLASSES.add(Short.class);
        IMMUTABLE_CLASSES.add(Integer.class);
        IMMUTABLE_CLASSES.add(Long.class);
        IMMUTABLE_CLASSES.add(Float.class);
        IMMUTABLE_CLASSES.add(Double.class);
        IMMUTABLE_CLASSES.add(BigDecimal.class);
        IMMUTABLE_CLASSES.add(BigInteger.class);
        IMMUTABLE_CLASSES.add(AtomicInteger.class);
        IMMUTABLE_CLASSES.add(AtomicLong.class);
    }

    /**
     * Clones the given object if it is a <tt>Cloneable</tt> instance and its <tt>clone()</tt> method is public.
     *
     * @param value the object to clone.
     * @return a clone of the given value.
     * @throws IllegalArgumentException   thrown if the value is null.
     * @throws CloneNotSupportedException thrown if the value does not implement <tt>Cloneable</bb> or if the
     *                                    <tt>clone()</tt> method is not public.
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) throws IllegalArgumentException, CloneNotSupportedException {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be NULL");
        }
        if (IMMUTABLE_CLASSES.contains(value.getClass())) {
            return value;
        }
        if (!(value instanceof Cloneable)) {
            throw new CloneNotSupportedException("'" + value.getClass().getName() + "' does not implement Cloneable");
        }
        try {
            Method cloneMethod = value.getClass().getMethod("clone");
            if ((cloneMethod.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                return (T) cloneMethod.invoke(value);
            }
            else {
                throw new CloneNotSupportedException(value.getClass().getName());
            }
        }
        catch (Exception e) {
            throw new CloneNotSupportedException(value.getClass().getName());
        }
    }

}
