package net.java.rome2.atom;

public class AtomException extends Exception {

    public AtomException(String msg) {
        super(msg);
    }

    public AtomException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AtomException(Throwable cause) {
        super(cause);
    }
}
