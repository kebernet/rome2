package net.java.rome2.atom;

import java.util.Date;

public class AtomDate extends AtomElement<AtomDate> {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone"})
    public AtomDate clone() throws CloneNotSupportedException {
        AtomDate atomDate = super.clone();
        if (getDate() != null) {
            atomDate.setDate((Date) getDate().clone());
        }
        return atomDate;
    }

}
