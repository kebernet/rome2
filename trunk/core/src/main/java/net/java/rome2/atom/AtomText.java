/**
 * Copyright 2006 Alejandro Abdelnur.
 */
package net.java.rome2.atom;

import org.jdom.Element;

public class AtomText extends AtomElement<AtomText> {

    public static final String TEXT = AtomConstants.TEXT;

    public static final String HTML = AtomConstants.HTML;

    public static final String XHTML = AtomConstants.XHTML;

    private String type = TEXT;
    private String text;
    private String html;
    private Element xhtml;

    public AtomText clone() throws CloneNotSupportedException {
        AtomText atomText = super.clone();
        if (TEXT.equals(getType())) {
            atomText.setText(getText());
        }
        else if (HTML.equals(getType())) {
            atomText.setHtml(getHtml());
        }
        else {
            atomText.setXhtml(getXhtml());
        }
        return atomText;
    }

    public String getType() {
        return type;
    }

    public void setText(String text) {
        this.type = TEXT;
        this.text = text;
        this.html = null;
        this.xhtml = null;
    }

    public String getText() {
        return text;
    }

    public void setHtml(String html) {
        type = (html != null) ? HTML : TEXT;
        this.html = html;
        this.text = null;
        this.xhtml = null;
    }

    public String getHtml() {
        return html;
    }

    public void setXhtml(Element xhtml) {
        if (xhtml != null &&
            !(AtomConstants.XHTML_NS_URI.equals(xhtml.getNamespaceURI()) && xhtml.getName().equals("div"))) {
            throw new IllegalArgumentException("root element must be XHTML's div element");
        }
        type = (xhtml != null) ? XHTML : TEXT;
        if (xhtml != null) {
            this.xhtml = (Element) xhtml.clone();
            this.xhtml.detach();
        }
        else {
            this.xhtml = null;
        }
        this.text = null;
        this.html = null;
    }

    public Element getXhtml() {
        return xhtml;
    }

}
