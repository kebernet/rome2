package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomCategory;
import net.java.rome2.atom.AtomConstants;
import net.java.rome2.atom.AtomContent;
import net.java.rome2.atom.AtomDate;
import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomFeed;
import net.java.rome2.atom.AtomGenerator;
import net.java.rome2.atom.AtomIcon;
import net.java.rome2.atom.AtomId;
import net.java.rome2.atom.AtomLink;
import net.java.rome2.atom.AtomLogo;
import net.java.rome2.atom.AtomPerson;
import net.java.rome2.atom.AtomSource;
import net.java.rome2.atom.AtomText;
import net.java.rome2.atom.Extensible;
import net.java.rome2.atom.spi.AtomParser;
import net.java.rome2.atom.spi.AtomParserContext;
import net.java.rome2.atom.spi.ExtensionParser;
import net.java.rome2.utils.Base64;
import net.java.rome2.utils.DateUtils;
import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.filter.ElementFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AtomParserImpl implements AtomParser {

    //works with lists of JDOM Elements and JDOM Attributes
    @SuppressWarnings({"unchecked"})
    private Set<Namespace> getNamespaces(List list, boolean isElement) {
        Set<Namespace> namespaces = null;
        for (Object o : list) {
            if (namespaces == null) {
                namespaces = new LinkedHashSet<Namespace>();
            }
            Namespace ns = (isElement) ? ((Element) o).getNamespace() : ((Attribute) o).getNamespace();
            if (!namespaces.contains(ns)) {
                namespaces.add(ns);
            }
        }
        return (namespaces != null) ? namespaces : Collections.EMPTY_SET;
    }

    @SuppressWarnings({"unchecked"})
    private List<Attribute> getAttributes(Element element, Namespace namespace, boolean includeNoNamespace) {
        List<Attribute> nsList = null;
        for (Attribute jAttr : (List<Attribute>) element.getAttributes()) {
            if (namespace.equals(jAttr.getNamespace()) ||
                (includeNoNamespace && jAttr.getNamespace().equals(Namespace.NO_NAMESPACE))) {
                if (nsList == null) {
                    nsList = new ArrayList<Attribute>();
                }
                nsList.add(jAttr);
            }
        }
        return (nsList != null) ? nsList : Collections.EMPTY_LIST;
    }

    @SuppressWarnings({"unchecked"})
    private List<Element> getElements(Element element, Namespace namespace) {
        return (List<Element>) element.getContent(new ElementFilter(namespace));
    }

    @SuppressWarnings({"unchecked"})
    private void parseXmlAttributes(AtomParserContext context, Element jElement, AtomElement aElement) {
        for (Attribute jAttr : getAttributes(jElement, Namespace.XML_NAMESPACE, false)) {
            if (jAttr.getName().equals(AtomConstants.XML_BASE)) {
                aElement.setXmlBase(jAttr.getValue());
            }
            else if (jAttr.getName().equals(AtomConstants.XML_BASE)) {
                aElement.setXmlLang(jAttr.getValue());
            }
            else {
                context.getReporter().report("WARN - unhandled XML attribute: " + jAttr.getName());
            }
        }
    }

    private void parseExtensions(AtomParserContext context, Element jElement, Extensible aElement)
            throws AtomException {
        for (Namespace ns : getNamespaces(jElement.getChildren(), true)) {
            if (!ns.equals(context.getAtomNamespace())) {
                List<Element> nsList = getElements(jElement, ns);
                if (!context.getNamespacePrefixMap().containsKey(ns.getURI())) {
                    context.getNamespacePrefixMap().put(ns.getURI(), ns.getPrefix());
                }
                ExtensionParser extensionParser = context.createExtensionParser(ns.getURI());
                if (extensionParser != null) {
                    extensionParser.parse(context, nsList, aElement);
                }
            }
        }
    }

    private void parseExtensionAttributes(AtomParserContext context, Element jElement, AtomElement atomElement)
            throws AtomException {
        for (Namespace ns : getNamespaces(jElement.getAttributes(), false)) {
            if (!ns.equals(context.getAtomNamespace()) && !ns.equals(Namespace.XML_NAMESPACE) &&
                !ns.equals(Namespace.NO_NAMESPACE)) {
                List<Attribute> nsAttributes = getAttributes(jElement, ns, false);
                if (!context.getNamespacePrefixMap().containsKey(ns.getURI())) {
                    context.getNamespacePrefixMap().put(ns.getURI(), ns.getPrefix());
                }
                ExtensionParser extensionParser = context.createExtensionParser(ns.getURI());
                if (extensionParser != null) {
                    extensionParser.parseAttributes(context, jElement, nsAttributes, atomElement);
                }
            }
        }
    }

    private void commonParsing(AtomParserContext context, Element jElement, AtomElement aElement, boolean extensible)
            throws AtomException {
        parseXmlAttributes(context, jElement, aElement);
        parseExtensionAttributes(context, jElement, aElement);
        if (extensible) {
            parseExtensions(context, jElement, ((Extensible) aElement));
        }
    }


    public void parseFeed(AtomParserContext context, Element jFeed, AtomFeed aFeed) throws AtomException {
        parseSource(context, jFeed, aFeed);
    }

    @SuppressWarnings({"unchecked"})
    public void parseEntry(AtomParserContext context, Element jEntry, AtomEntry aEntry) throws AtomException {

        commonParsing(context, jEntry, aEntry, true);

        for (Element jChild : getElements(jEntry, context.getAtomNamespace())) {
            if (jChild.getName().equals("author")) {
                aEntry.getAuthors().add(parsePerson(context, jChild));
            }
            else if (jChild.getName().equals("category")) {
                aEntry.getCategories().add(parseCategory(context, jChild));
            }
            else if (jChild.getName().equals("content")) {
                aEntry.setContent(parseContent(context, jChild));
            }
            else if (jChild.getName().equals("contributor")) {
                aEntry.getContributors().add(parsePerson(context, jChild));
            }
            else if (jChild.getName().equals("id")) {
                aEntry.setId(parseId(context, jChild));
            }
            else if (jChild.getName().equals("link")) {
                aEntry.getLinks().add(parseLink(context, jChild));
            }
            else if (jChild.getName().equals("published")) {
                aEntry.setPublished(parseDate(context, jChild));
            }
            else if (jChild.getName().equals("rights")) {
                aEntry.setRights(parseText(context, jChild));
            }
            else if (jChild.getName().equals("source")) {
                AtomSource source = new AtomSource();
                aEntry.setSource(parseSource(context, jChild, source));
            }
            else if (jChild.getName().equals("summary")) {
                aEntry.setSummary(parseText(context, jChild));
            }
            else if (jChild.getName().equals("title")) {
                aEntry.setTitle(parseText(context, jChild));
            }
            else if (jChild.getName().equals("updated")) {
                aEntry.setUpdated(parseDate(context, jChild));
            }
            else {
                context.getReporter().report("WARN - unknown Atom entry element: " + jChild.getName());
            }
        }

    }

    @SuppressWarnings({"unchecked"})
    private AtomPerson parsePerson(AtomParserContext context, Element jPerson) throws AtomException {
        AtomPerson aPerson = new AtomPerson();

        commonParsing(context, jPerson, aPerson, true);

        for (Element jChild : getElements(jPerson, context.getAtomNamespace())) {
            if (jChild.getName().equals("name")) {
                aPerson.setName(jChild.getText());
            }
            else if (jChild.getName().equals("uri")) {
                aPerson.setUri(jChild.getText());
            }
            else if (jChild.getName().equals("email")) {
                aPerson.setEmail(jChild.getText());
            }
            else {
                context.getReporter().report("WARN - unknown Atom person element: " + jChild.getName());
            }
        }

        return aPerson;
    }

    @SuppressWarnings({"unchecked"})
    private AtomCategory parseCategory(AtomParserContext context, Element jCategory) throws AtomException {
        AtomCategory aCategory = new AtomCategory();

        commonParsing(context, jCategory, aCategory, true);

        for (Attribute jAttr : getAttributes(jCategory, context.getAtomNamespace(), true)) {
            if (jAttr.getName().equals("term")) {
                aCategory.setTerm(jAttr.getValue());
            }
            else if (jAttr.getName().equals("scheme")) {
                aCategory.setScheme(jAttr.getValue());
            }
            else if (jAttr.getName().equals("label")) {
                aCategory.setLabel(jAttr.getValue());
            }
            else {
                context.getReporter().report("WARN - unknown Atom category attribute: " + jAttr.getName());
            }
        }

        return aCategory;
    }

    @SuppressWarnings({"unchecked"})
    private AtomContent parseContent(AtomParserContext context, Element jContent) throws AtomException {
        AtomContent aContent = new AtomContent();

        commonParsing(context, jContent, aContent, false);

        for (Attribute jAttr : getAttributes(jContent, context.getAtomNamespace(), true)) {
            if (jAttr.getName().equals("type")) {
                aContent.setType(jAttr.getValue());
            }
            else if (jAttr.getName().equals("src")) {
                aContent.setSrc(jAttr.getValue());
            }
        }

        if (aContent.getSrc() == null) {
            String type = aContent.getType();
            if (type == null || type.equals(AtomContent.TEXT)) {
                aContent.setText(jContent.getText());
            }
            else if (type.equals(AtomContent.HTML)) {
                aContent.setHtml(jContent.getText());
            }
            else if (type.equals(AtomContent.XHTML)) {
                aContent.setXhtml(getXmlElement(context, jContent.getContent()));
            }
            else if (type.endsWith("-xml") || type.endsWith("/xml")) {
                aContent.setXml(getXmlElement(context, jContent.getContent()));
            }
            else {
                aContent.setBinary(Base64.decodeBytes(jContent.getText().trim()));
            }

        }
        return aContent;
    }

    @SuppressWarnings({"unchecked"})
    private AtomId parseId(AtomParserContext context, Element jId) throws AtomException {
        AtomId aId = new AtomId();

        commonParsing(context, jId, aId, false);

        if (jId.getText().length() > 0) {
            aId.setId(jId.getText());
        }

        return aId;
    }

    @SuppressWarnings({"unchecked"})
    private AtomLink parseLink(AtomParserContext context, Element jLink) throws AtomException {
        AtomLink aLink = new AtomLink();

        commonParsing(context, jLink, aLink, false);

        for (Attribute jAttr : getAttributes(jLink, context.getAtomNamespace(), true)) {
            if (jAttr.getName().equals("href")) {
                aLink.setHref(jAttr.getValue());
            }
            else if (jAttr.getName().equals("hreflang")) {
                aLink.setHreflang(jAttr.getValue());
            }
            else if (jAttr.getName().equals("length")) {
                long len = -1;
                try {
                    len = jAttr.getLongValue();
                }
                catch (DataConversionException ex) {
                    context.getReporter()
                            .report("WARN - losing data, invalid 'length' value: " + jAttr.getValue());
                }
                aLink.setLength(len);
            }
            else if (jAttr.getName().equals("rel")) {
                aLink.setRel(jAttr.getValue());
            }
            else if (jAttr.getName().equals("title")) {
                aLink.setTitle(jAttr.getValue());
            }
            else if (jAttr.getName().equals("type")) {
                aLink.setType(jAttr.getValue());
            }
            else {
                context.getReporter().report("WARN - unknown Atom link attribute: " + jAttr.getName());
            }
        }

        return aLink;
    }

    private AtomDate parseDate(AtomParserContext context, Element jDate) throws AtomException {
        AtomDate atomDate = new AtomDate();

        commonParsing(context, jDate, atomDate, false);

        String strDate = jDate.getTextTrim();
        if (strDate.length() > 0) {
            DateUtils dateUtils = context.getDateUtils();
            Date date = dateUtils.parseW3CDateTime(strDate);
            if (date != null) {
                atomDate.setDate(date);
            }
            else {
                date = dateUtils.parseRFC822(strDate);
                if (date != null) {
                    context.getReporter().report("WARN - parsed as RFC822, it should be W3C: " + strDate);
                    atomDate.setDate(date);
                }
                else {
                    date = dateUtils.parseMasks(strDate);
                    if (date != null) {
                        context.getReporter()
                                .report("WARN - parsed using additional masks, it should be W3C: " + strDate);
                        atomDate.setDate(date);
                    }
                    else {
                        context.getReporter().report("WARN - losing data, could not parse date: " + strDate);
                    }
                }
            }
        }
        else {
            context.getReporter().report("WARN - empty date");
        }

        return atomDate;
    }

    private Element getXmlElement(AtomParserContext context, List jContent) {
        Element jRoot = null;
        if (jContent.size() == 1) {
            jRoot = (Element) jContent.get(0);
        }
        else if (jContent.size() == 2) {
            if (jContent.get(0) instanceof Element && jContent.get(1) instanceof org.jdom.Text) {
                jRoot = (Element) jContent.get(0);
            }
            else if (jContent.get(1) instanceof Element && jContent.get(0) instanceof org.jdom.Text) {
                jRoot = (Element) jContent.get(1);
            }
        }
        else if (jContent.size() == 3) {
            if (jContent.get(2) instanceof org.jdom.Text && jContent.get(1) instanceof Element &&
                jContent.get(2) instanceof org.jdom.Text) {
                jRoot = (Element) jContent.get(1);
            }
        }
        else {
            context.getReporter()
                    .report("WARN - losing data, there should be a single XML sub-element in text/content");
        }
        if (jRoot != null) {
            jRoot.detach();
            if (jRoot.getNamespaceURI() != null &&
                !context.getNamespacePrefixMap().containsKey(jRoot.getNamespaceURI())) {
                String prefix = jRoot.getNamespacePrefix();
                context.getNamespacePrefixMap().put(jRoot.getNamespaceURI(), prefix);
            }
        }
        return jRoot;
    }

    @SuppressWarnings({"unchecked"})
    private AtomText parseText(AtomParserContext context, Element jText) throws AtomException {
        AtomText aText = new AtomText();

        commonParsing(context, jText, aText, false);

        String type = null;
        for (Attribute jAttr : getAttributes(jText, context.getAtomNamespace(), true)) {
            if (jAttr.getName().equals("type")) {
                type = jAttr.getValue();
                if (!AtomText.TEXT.equals(type) && !AtomText.HTML.equals(type) & !AtomText.XHTML.equals(type)) {
                    context.getReporter().report("WARN - invalid text type: " + type);
                    type = type.toLowerCase();
                    if (!AtomText.TEXT.equals(type) && !AtomText.HTML.equals(type) & !AtomText.XHTML.equals(type)) {
                        context.getReporter().report("WARN - invalid text type after lowercasing: " + type);
                        type = null;
                    }
                }
            }
        }
        if (type == null || type.equals(AtomText.TEXT)) {
            aText.setText(jText.getText());
        }
        else if (type.equals(AtomText.HTML)) {
            aText.setHtml(jText.getText());
        }
        else {
            Element div = getXmlElement(context, jText.getContent());
            if (div != null) {
                aText.setXhtml(div);
            }
        }

        return aText;
    }

    @SuppressWarnings({"unchecked"})
    private AtomGenerator parseGenerator(AtomParserContext context, Element jGenerator) throws AtomException {
        AtomGenerator aGenerator = new AtomGenerator();

        commonParsing(context, jGenerator, aGenerator, false);

        for (Attribute jAttr : getAttributes(jGenerator, context.getAtomNamespace(), true)) {
            if (jAttr.getName().equals("uri")) {
                aGenerator.setUri(jAttr.getValue());
            }
            else if (jAttr.getName().equals("version")) {
                aGenerator.setVersion(jAttr.getValue());
            }
            else {
                context.getReporter().report("WARN - unknown Atom generator attribute: " + jAttr.getName());
            }
        }

        aGenerator.setText(jGenerator.getText());

        return aGenerator;
    }

    private AtomSource parseSource(AtomParserContext context, Element jSource, AtomSource aSource)
            throws AtomException {
        commonParsing(context, jSource, aSource, true);

        for (Element jChild : getElements(jSource, context.getAtomNamespace())) {
            if (jChild.getName().equals("author")) {
                aSource.getAuthors().add(parsePerson(context, jChild));
            }
            else if (jChild.getName().equals("category")) {
                aSource.getCategories().add(parseCategory(context, jChild));
            }
            else if (jChild.getName().equals("contributor")) {
                aSource.getContributors().add(parsePerson(context, jChild));
            }
            else if (jChild.getName().equals("generator")) {
                aSource.setGenerator(parseGenerator(context, jChild));
            }
            else if (jChild.getName().equals("icon")) {
                aSource.setIcon(parseIcon(context, jChild));
            }
            else if (jChild.getName().equals("logo")) {
                aSource.setLogo(parseLogo(context, jChild));
            }
            else if (jChild.getName().equals("id")) {
                aSource.setId(parseId(context, jChild));
            }
            else if (jChild.getName().equals("link")) {
                aSource.getLinks().add(parseLink(context, jChild));
            }
            else if (jChild.getName().equals("rights")) {
                aSource.setRights(parseText(context, jChild));
            }
            else if (jChild.getName().equals("title")) {
                aSource.setTitle(parseText(context, jChild));
            }
            else if (jChild.getName().equals("subtitle")) {
                aSource.setSubtitle(parseText(context, jChild));
            }
            else if (jChild.getName().equals("updated")) {
                aSource.setUpdated(parseDate(context, jChild));
            }
            else if (jChild.getName().equals("entry") && aSource instanceof AtomFeed) {
                //IGNORE
            }
            else {
                context.getReporter().report("WARN - unknown Atom feed/source element: " + jChild.getName());
            }
        }
        return aSource;
    }

    private AtomIcon parseIcon(AtomParserContext context, Element jLogo) throws AtomException {
        AtomIcon aLogo = new AtomIcon();
        commonParsing(context, jLogo, aLogo, false);
        aLogo.setIcon(jLogo.getText());
        return aLogo;
    }

    private AtomLogo parseLogo(AtomParserContext context, Element jLogo) throws AtomException {
        AtomLogo aLogo = new AtomLogo();
        commonParsing(context, jLogo, aLogo, false);
        aLogo.setLogo(jLogo.getText());
        return aLogo;
    }

}
