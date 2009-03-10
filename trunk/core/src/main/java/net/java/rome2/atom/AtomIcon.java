package net.java.rome2.atom;

public class AtomIcon extends AtomElement<AtomIcon> {
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AtomIcon clone() throws CloneNotSupportedException {
        AtomIcon icon = super.clone();
        icon.setIcon(getIcon());
        return icon;
    }

}
