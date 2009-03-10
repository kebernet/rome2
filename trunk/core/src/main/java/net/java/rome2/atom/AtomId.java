package net.java.rome2.atom;

public class AtomId extends AtomElement<AtomId> {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public AtomId clone() throws CloneNotSupportedException {
        AtomId id = super.clone();
        id.setId(getId());
        return id;
    }

}
