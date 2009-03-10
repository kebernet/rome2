package net.java.rome2.atom;

public class AtomLogo extends AtomElement<AtomLogo> {
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public AtomLogo clone() throws CloneNotSupportedException {
        AtomLogo logo = super.clone();
        logo.setLogo(getLogo());
        return logo;
    }
}
