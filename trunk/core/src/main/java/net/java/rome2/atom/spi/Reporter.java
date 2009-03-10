package net.java.rome2.atom.spi;

public interface Reporter {

    boolean isEnabled();

    void report(String msg);

}
