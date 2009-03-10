package net.java.rome2.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <tt>ArrayList</tt> subclass that does not accept null values.
 */
public class NotNullElementsArrayList<E> extends ArrayList<E> {

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public NotNullElementsArrayList() {
        super();
    }

    /**
     * Constructs a list containing the elements of the specified collection, in the order they are returned by the
     * collection's iterator.  The <tt>ArrayList</tt> instance has an initial capacity of 110% the size of the specified
     * collection.
     *
     * @param collection the collection whose elements are to be placed into this list.
     *
     * @throws NullPointerException if the specified collection is null.
     */
    public NotNullElementsArrayList(Collection<E> collection) {
        super(collection);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list.
     *
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public NotNullElementsArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list.
     *
     * @return <tt>true</tt> (as per the general contract of Collection.add).
     */
    public boolean add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be NULL");
        }
        return super.add(element);
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently at that
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     *
     * @throws IndexOutOfBoundsException if index is out of range <tt>(index &lt; 0 || index &gt; size())</tt>.
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be NULL");
        }
        super.add(index, element);
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     *
     * @return the element previously at the specified position.
     *
     * @throws IndexOutOfBoundsException if index out of range <tt>(index &lt; 0 || index &gt;= size())</tt>.
     */
    public E set(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be NULL");
        }
        return super.set(index, element);
    }

}
