package net.java.rome2.atom;

public interface AtomExtension<E extends AtomExtension> {

    public E clone() throws CloneNotSupportedException;

    String getUri();

    String getPrefix();

}
