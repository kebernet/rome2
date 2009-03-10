package net.java.rome2.impl.atom.spi;

import net.java.rome2.atom.AtomCategory;
import net.java.rome2.atom.AtomConstants;
import net.java.rome2.atom.AtomContent;
import net.java.rome2.atom.AtomDate;
import net.java.rome2.atom.AtomElement;
import net.java.rome2.atom.AtomEntry;
import net.java.rome2.atom.AtomException;
import net.java.rome2.atom.AtomExtAttr;
import net.java.rome2.atom.AtomExtension;
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
import net.java.rome2.atom.spi.AtomGeneratorContext;
import net.java.rome2.atom.spi.ExtensionGenerator;
import net.java.rome2.utils.Base64;
import org.jdom.Element;
import org.jdom.Namespace;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AtomGeneratorImpl implements net.java.rome2.atom.spi.AtomGenerator {

    public void generateFeed(AtomGeneratorContext context, AtomFeed aFeed, Element jFeed) throws AtomException {
        generateSource(context, aFeed, jFeed);
    }

    private void generateXmlAttributes(Element xmlElement, AtomElement beanElement) {
        if (beanElement.getXmlBase() != null) {
            xmlElement.setAttribute("base", beanElement.getXmlBase(), Namespace.XML_NAMESPACE);
        }
        if (beanElement.getXmlLang() != null) {
            xmlElement.setAttribute("lang", beanElement.getXmlLang(), Namespace.XML_NAMESPACE);
        }
    }

    @SuppressWarnings({"unchecked"})
    private Set<String> getExtensionAttributeNamespaces(AtomElement aElement) {
        Set<String> namespaces = null;
        for (AtomExtAttr aExtAttr : (Set<AtomExtAttr>) aElement.getExtensionAttributes()) {
            if (namespaces == null) {
                namespaces = new LinkedHashSet<String>();
            }
            if (!namespaces.contains(aExtAttr.getNamespace())) {
                namespaces.add(aExtAttr.getNamespace());
            }
        }
        return (namespaces != null) ? namespaces : Collections.EMPTY_SET;
    }

    @SuppressWarnings({"unchecked"})
    private void generateExtensionAttributes(AtomGeneratorContext context, AtomElement aElement, Element jElement)
            throws AtomException {

        for (String ns : getExtensionAttributeNamespaces(aElement)) {
            ExtensionGenerator extGen = context.createExtensionGenerator(ns);
            if (extGen != null) {
                extGen.generateAttributes(context, aElement, aElement.getExtensionAttributes(ns), jElement);
            }

        }
    }


    private void generateExtensions(AtomGeneratorContext context, Extensible aElement, Element jElement)
            throws AtomException {

        for (AtomExtension ext : aElement.getExtensions()) {
            ExtensionGenerator extGen = context.createExtensionGenerator(ext.getUri());
            if (extGen != null) {
                if (extGen.canGenerate(ext.getClass())) {
                    //noinspection unchecked
                    extGen.generate(context, aElement, ext, jElement);
                }
                else {
                    throw new AtomException("ExtensionGenerator for '" + ext.getUri() +
                                            "' does not handle extension: " + ext.getClass().getName());
                }
            }
            else {
                context.getReporter().report("WARN - Missing ExtensionGenerator for: " + ext.getUri());
            }
        }
    }

    private void commonGeneration(AtomGeneratorContext context, AtomElement aElement, Element jElement,
                                  boolean extensible) throws AtomException {
        generateXmlAttributes(jElement, aElement);
        generateExtensionAttributes(context, aElement, jElement);
        if (extensible) {
            generateExtensions(context, (Extensible) aElement, jElement);
        }
    }


    private void generateSource(AtomGeneratorContext context, AtomSource aSource, Element jSource)
            throws AtomException {
        for (AtomPerson author : aSource.getAuthors()) {
            generatePerson(context, author, "author", jSource);
        }
        for (AtomCategory category : aSource.getCategories()) {
            generateCategory(context, category, jSource);
        }
        if (aSource.getGenerator() != null) {
            generateGenerator(context, aSource.getGenerator(), jSource);
        }
        if (aSource.getIcon() != null) {
            generateIcon(context, aSource.getIcon(), jSource);
        }
        if (aSource.getLogo() != null) {
            generateLogo(context, aSource.getLogo(), jSource);
        }
        if (aSource.getId() != null) {
            generateId(context, aSource.getId(), jSource);
        }
        for (AtomLink link : aSource.getLinks()) {
            generateLink(context, link, jSource);
        }
        if (aSource.getRights() != null) {
            generateText(context, aSource.getRights(), "rights", jSource);
        }
        if (aSource.getTitle() != null) {
            generateText(context, aSource.getTitle(), "title", jSource);
        }
        if (aSource.getSubtitle() != null) {
            generateText(context, aSource.getSubtitle(), "subtitle", jSource);
        }
        if (aSource.getUpdated() != null) {
            generateAtomDate(context, aSource.getUpdated(), "updated", jSource);
        }

        commonGeneration(context, aSource, jSource, true);
    }

    private void generateCategory(AtomGeneratorContext context, AtomCategory aCategory, Element jRoot)
            throws AtomException {
        Element jCategory = new Element("category", context.getAtomNamespace());
        jRoot.addContent(jCategory);

        if (aCategory.getTerm() != null) {
            jCategory.setAttribute("term", aCategory.getTerm());
        }
        if (aCategory.getScheme() != null) {
            jCategory.setAttribute("scheme", aCategory.getScheme());
        }
        if (aCategory.getLabel() != null) {
            jCategory.setAttribute("label", aCategory.getLabel());
        }

        commonGeneration(context, aCategory, jCategory, true);
    }

    private void generateGenerator(AtomGeneratorContext context, AtomGenerator aGenerator, Element jRoot)
            throws AtomException {
        Element jGenerator = new Element("generator", context.getAtomNamespace());
        jRoot.addContent(jGenerator);

        if (aGenerator.getUri() != null) {
            jGenerator.setAttribute("uri", aGenerator.getUri());
        }
        if (aGenerator.getVersion() != null) {
            jGenerator.setAttribute("version", aGenerator.getVersion());
        }

        if (aGenerator.getText() != null) {
            jGenerator.setText(aGenerator.getText());
        }

        commonGeneration(context, aGenerator, jGenerator, false);
    }

    private void generateIcon(AtomGeneratorContext context, AtomIcon aIcon, Element jRoot) throws AtomException {
        Element jIcon = new Element("icon", context.getAtomNamespace());
        jRoot.addContent(jIcon);

        if (aIcon.getIcon() != null) {
            jIcon.setText(aIcon.getIcon());
        }

        commonGeneration(context, aIcon, jIcon, false);
    }

    private void generateLogo(AtomGeneratorContext context, AtomLogo aLogo, Element jRoot) throws AtomException {
        Element jLogo = new Element("logo", context.getAtomNamespace());
        jRoot.addContent(jLogo);

        if (aLogo.getLogo() != null) {
            jLogo.setText(aLogo.getLogo());
        }

        commonGeneration(context, aLogo, jLogo, false);
    }

    private void generateId(AtomGeneratorContext context, AtomId aId, Element jRoot) throws AtomException {
        Element jId = new Element("id", context.getAtomNamespace());
        jRoot.addContent(jId);

        if (aId.getId() != null) {
            jId.setText(aId.getId());
        }

        commonGeneration(context, aId, jId, false);
    }

    private void generateLink(AtomGeneratorContext context, AtomLink aLink, Element jRoot) throws AtomException {
        Element jLink = new Element("link", context.getAtomNamespace());
        jRoot.addContent(jLink);

        if (aLink.getHref() != null) {
            jLink.setAttribute("href", aLink.getHref());
        }
        if (aLink.getHreflang() != null) {
            jLink.setAttribute("hreflang", aLink.getHreflang());
        }
        if (aLink.getLength() > -1) {
            jLink.setAttribute("length", Long.toString(aLink.getLength()));
        }
        if (aLink.getRel() != null) {
            jLink.setAttribute("rel", aLink.getRel());
        }
        if (aLink.getTitle() != null) {
            jLink.setAttribute("title", aLink.getTitle());
        }
        if (aLink.getType() != null) {
            jLink.setAttribute("type", aLink.getType());
        }

        commonGeneration(context, aLink, jLink, false);
    }

    private void generateText(AtomGeneratorContext context, AtomText aText, String jName, Element jRoot)
            throws AtomException {

        Element jText = new Element(jName, context.getAtomNamespace());
        jRoot.addContent(jText);

        if (aText.getType() != null) {
            jText.setAttribute("type", aText.getType());
        }

        if (aText.getText() != null) {
            jText.setText(aText.getText());
        }

        if (aText.getXhtml() != null) {
            Namespace ns = context.getNamespace(AtomConstants.XHTML_NS_DEFAULT_PREFIX, AtomConstants.XHTML_NS_URI);
            Element div = (Element) aText.getXhtml().clone();
            deepNamespaceReset(div, ns);
            jText.setContent(div);
        }

        commonGeneration(context, aText, jText, false);
    }

    @SuppressWarnings({"unchecked"})
    private void deepNamespaceReset(Element jElement, Namespace ns) {
        if (ns.getURI().equals(jElement.getNamespaceURI())) {
            jElement.setNamespace(ns);
            for (org.jdom.Attribute xmlAttr : (List<org.jdom.Attribute>) jElement.getAttributes()) {
                if (ns.getURI().equals(xmlAttr.getNamespaceURI())) {
                    xmlAttr.setNamespace(ns);
                }
            }
            for (org.jdom.Content xmlContent : (List<org.jdom.Content>) jElement.getChildren()) {
                if (xmlContent instanceof Element) {
                    deepNamespaceReset((Element) xmlContent, ns);
                }
            }
        }
    }

    private void generateContent(AtomGeneratorContext context, AtomContent aContent, Element jRoot)
            throws AtomException {
        Element jContent = new Element("content", context.getAtomNamespace());
        jRoot.addContent(jContent);

        if (aContent.getType() != null) {
            jContent.setAttribute("type", aContent.getType());
        }
        if (aContent.getSrc() != null) {
            jContent.setAttribute("src", aContent.getSrc());
        }

        if (aContent.getText() != null) {
            jContent.setText(aContent.getText());
        }
        else if (aContent.getHtml() != null) {
            jContent.setText(aContent.getText());
        }
        else if (aContent.getXhtml() != null) {
            Element xhtml = (Element) aContent.getXhtml().clone();
            Namespace ns = context.getNamespace(xhtml.getNamespacePrefix(), xhtml.getNamespaceURI());
            deepNamespaceReset(xhtml, ns);
            jContent.setContent(xhtml);
        }
        else if (aContent.getXml() != null) {
            Element xml = (Element) aContent.getXml().clone();
            Namespace ns = context.getNamespace(xml.getNamespacePrefix(), xml.getNamespaceURI());
            deepNamespaceReset(xml, ns);
            jContent.setContent(xml);
        }
        else if (aContent.getBinary() != null) {
            jContent.setText(Base64.encodeBytes(aContent.getBinary(), true));
        }

        commonGeneration(context, aContent, jContent, false);
    }

    private void generateAtomDate(AtomGeneratorContext context, AtomDate aDate, String jName, Element jRoot)
            throws AtomException {
        Element jDate = new Element(jName, context.getAtomNamespace());
        jRoot.addContent(jDate);

        if (aDate.getDate() != null) {
            jDate.setText(context.getDateUtils().formatW3CDateTime(aDate.getDate()));
        }

        commonGeneration(context, aDate, jDate, false);
    }

    private void generatePerson(AtomGeneratorContext context, AtomPerson aPerson, String jName, Element jRoot)
            throws AtomException {
        Element jPerson = new Element(jName, context.getAtomNamespace());
        jRoot.addContent(jPerson);

        if (aPerson.getName() != null) {
            Element eName = new Element("name", context.getAtomNamespace());
            eName.setText(aPerson.getName());
            jPerson.addContent(eName);
        }
        if (aPerson.getEmail() != null) {
            Element eEMail = new Element("email", context.getAtomNamespace());
            eEMail.setText(aPerson.getEmail());
            jPerson.addContent(eEMail);
        }
        if (aPerson.getName() != null) {
            Element eUri = new Element("uri", context.getAtomNamespace());
            eUri.setText(aPerson.getUri());
            jPerson.addContent(eUri);
        }

        commonGeneration(context, aPerson, jPerson, true);
    }

    public void generateEntry(AtomGeneratorContext context, AtomEntry aEntry, Element jEntry) throws AtomException {

        for (AtomPerson author : aEntry.getAuthors()) {
            generatePerson(context, author, "author", jEntry);
        }
        for (AtomCategory category : aEntry.getCategories()) {
            generateCategory(context, category, jEntry);
        }
        if (aEntry.getContent() != null) {
            generateContent(context, aEntry.getContent(), jEntry);
        }
        for (AtomPerson contributor : aEntry.getContributors()) {
            generatePerson(context, contributor, "contributor", jEntry);
        }
        if (aEntry.getId() != null) {
            generateId(context, aEntry.getId(), jEntry);
        }
        for (AtomLink link : aEntry.getLinks()) {
            generateLink(context, link, jEntry);
        }
        if (aEntry.getPublished() != null) {
            generateAtomDate(context, aEntry.getPublished(), "published", jEntry);
        }
        if (aEntry.getRights() != null) {
            generateText(context, aEntry.getRights(), "rights", jEntry);
        }
        if (aEntry.getSource() != null) {
            Element xmlSource = new Element("source", context.getAtomNamespace());
            jEntry.addContent(xmlSource);
            generateSource(context, aEntry.getSource(), xmlSource);
        }
        if (aEntry.getSummary() != null) {
            generateText(context, aEntry.getSummary(), "summary", jEntry);
        }
        if (aEntry.getTitle() != null) {
            generateText(context, aEntry.getTitle(), "title", jEntry);
        }
        if (aEntry.getUpdated() != null) {
            generateAtomDate(context, aEntry.getPublished(), "udpated", jEntry);
        }

        commonGeneration(context, aEntry, jEntry, true);
    }

}
