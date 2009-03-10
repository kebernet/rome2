package net.java.rome2.atom;

public class TestAtomSource extends BeanTestCase {

    public void testBean() {
        AtomSource Source = new AtomSource();
        assertEquals(0, Source.getAuthors().size());
        assertEquals(0, Source.getCategories().size());
        assertEquals(0, Source.getContributors().size());
        assertEquals(null, Source.getGenerator());
        assertEquals(null, Source.getIcon());
        assertEquals(null, Source.getLogo());
        assertEquals(null, Source.getId());
        assertEquals(0, Source.getLinks().size());
        assertEquals(null, Source.getRights());
        assertEquals(null, Source.getTitle());
        assertEquals(null, Source.getSubtitle());
        assertEquals(null, Source.getUpdated());
        assertEquals(0, Source.getExtensions().size());

        Source.getAuthors().add(new AtomPerson());
        assertEquals(1, Source.getAuthors().size());

        Source.getCategories().add(new AtomCategory());
        assertEquals(1, Source.getCategories().size());

        Source.getContributors().add(new AtomPerson());
        assertEquals(1, Source.getContributors().size());

        Source.setGenerator(new AtomGenerator());
        assertNotNull(Source.getGenerator());

        Source.setIcon(new AtomIcon());
        assertNotNull(Source.getIcon());

        Source.setLogo(new AtomLogo());
        assertNotNull(Source.getLogo());

        Source.setId(new AtomId());
        assertNotNull(Source.getId());

        Source.getLinks().add(new AtomLink());
        assertEquals(1, Source.getLinks().size());

        Source.setRights(new AtomText());
        assertNotNull(Source.getRights());

        Source.setTitle(new AtomText());
        assertNotNull(Source.getTitle());

        Source.setSubtitle(new AtomText());
        assertNotNull(Source.getSubtitle());

        Source.setUpdated(new AtomDate());
        assertNotNull(Source.getUpdated());

        Source.getExtensions().add(new UnknownAtomExtension("a", "A"));
        assertEquals(1, Source.getExtensions().size());
    }

    protected static void seedSource(AtomSource source) {
        source.getAuthors().add(new AtomPerson());
        source.getCategories().add(new AtomCategory());
        source.getContributors().add(new AtomPerson());
        source.setGenerator(new AtomGenerator());
        source.setIcon(new AtomIcon());
        source.setLogo(new AtomLogo());
        source.setId(new AtomId());
        source.getLinks().add(new AtomLink());
        source.setRights(new AtomText());
        source.setTitle(new AtomText());
        source.setSubtitle(new AtomText());
        source.setUpdated(new AtomDate());
        source.getExtensions().add(new UnknownAtomExtension("a", "A"));
    }

    public void testClone() throws Exception {
        AtomSource source = new AtomSource();

        seedSource(source);

        assertEquals(source, source.clone());
    }

    public static void assertEquals(AtomSource source1, AtomSource source2) {
        if (notNull(source1, source2)) {
            TestAtomElement.assertEquals(source1, source2);

            assertListEquals(source1.getAuthors(), source2.getAuthors(), TestAtomPerson.class, AtomPerson.class);
            assertListEquals(source1.getCategories(), source2.getCategories(), TestAtomCategory.class,
                             AtomCategory.class);
            assertListEquals(source1.getContributors(), source2.getContributors(), TestAtomPerson.class,
                             AtomPerson.class);
            TestAtomGenerator.assertEquals(source1.getGenerator(), source2.getGenerator());
            TestAtomIcon.assertEquals(source1.getIcon(), source2.getIcon());
            TestAtomLogo.assertEquals(source1.getLogo(), source2.getLogo());
            TestAtomId.assertEquals(source1.getId(), source2.getId());
            assertListEquals(source1.getLinks(), source2.getLinks(), TestAtomLink.class, AtomLink.class);
            TestAtomText.assertEquals(source1.getRights(), source2.getRights());
            TestAtomText.assertEquals(source1.getTitle(), source2.getTitle());
            TestAtomText.assertEquals(source1.getSubtitle(), source2.getSubtitle());
            TestAtomDate.assertEquals(source1.getUpdated(), source2.getUpdated());

            TestExtensibleImpl.assertEquals(source1, source2);
        }
    }

}
