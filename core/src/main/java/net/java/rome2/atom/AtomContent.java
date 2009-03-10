package net.java.rome2.atom;

import org.jdom.Element;

public class AtomContent extends AtomElement<AtomContent> {

    public static final String TEXT = AtomConstants.TEXT;

    public static final String HTML = AtomConstants.HTML;

    public static final String XHTML = AtomConstants.XHTML;

    private String type;
    private String src;
    private String text;
    private String html;
    private Element xhtml;
    private Element xml;
    private byte[] binary;

    public AtomContent clone() throws CloneNotSupportedException {
        AtomContent content = super.clone();
        content.setType(type);
        if (src != null) {
            content.setSrc(src);
        }
        else if (text != null) {
            content.setText(text);
        }
        else if (html != null) {
            content.setHtml(html);
        }
        else if (xhtml != null) {
            content.setXhtml(xhtml);
        }
        else if (xml != null) {
            content.setXml(xml);
        }
        else if (binary != null) {
            content.setBinary(binary);
        }
        return content;
    }

    private void clearData() {
        text = null;
        html = null;
        xhtml = null;
        xml = null;
        binary = null;
        src = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        clearData();
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        clearData();
        type = TEXT;
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        clearData();
        type = HTML;
        this.html = html;
    }

    public Element getXml() {
        return xml;
    }

    public void setXml(Element xml) {
        clearData();
        if (xml != null) {
            this.xml = (Element) xml.clone();
            this.xml.detach();
        }
        else {
            this.xml = null;
        }
    }

    public Element getXhtml() {
        return xhtml;
    }

    public void setXhtml(Element xhtml) {
        if (xhtml != null &&
            !(AtomConstants.XHTML_NS_URI.equals(xhtml.getNamespaceURI()) && xhtml.getName().equals("div"))) {
            throw new IllegalArgumentException("root element must be XHTML's div element");
        }
        clearData();
        type = XHTML;
        if (xhtml != null) {
            this.xhtml = (Element) xhtml.clone();
            this.xhtml.detach();
        }
        else {
            this.xhtml = null;
        }
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] value) {
        clearData();
        binary = (value != null) ? value.clone() : null;
    }

}
