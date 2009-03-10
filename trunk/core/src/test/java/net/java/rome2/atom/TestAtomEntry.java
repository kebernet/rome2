package net.java.rome2.atom;

public class TestAtomEntry extends BeanTestCase {

    public void testBean() {
        AtomEntry entry = new AtomEntry();
        assertEquals(0, entry.getAuthors().size());
        assertEquals(0, entry.getCategories().size());
        assertEquals(null, entry.getContent());
        assertEquals(0, entry.getContributors().size());
        assertEquals(null, entry.getId());
        assertEquals(0, entry.getLinks().size());
        assertEquals(null, entry.getPublished());
        assertEquals(null, entry.getRights());
        assertEquals(null, entry.getSource());
        assertEquals(null, entry.getTitle());
        assertEquals(null, entry.getUpdated());
        assertEquals(0, entry.getExtensions().size());

        entry.getAuthors().add(new AtomPerson());
        assertEquals(1, entry.getAuthors().size());

        entry.getCategories().add(new AtomCategory());
        assertEquals(1, entry.getCategories().size());

        entry.setContent(new AtomContent());
        assertNotNull(entry.getContent());

        entry.getContributors().add(new AtomPerson());
        assertEquals(1, entry.getContributors().size());

        entry.setId(new AtomId());
        assertNotNull(entry.getId());

        entry.getLinks().add(new AtomLink());
        assertEquals(1, entry.getLinks().size());

        entry.setPublished(new AtomDate());
        assertNotNull(entry.getPublished());

        entry.setRights(new AtomText());
        assertNotNull(entry.getRights());

        entry.setSource(new AtomSource());
        assertNotNull(entry.getSource());

        entry.setTitle(new AtomText());
        assertNotNull(entry.getTitle());

        entry.setUpdated(new AtomDate());
        assertNotNull(entry.getUpdated());

        entry.getExtensions().add(new UnknownAtomExtension("a", "A"));
        assertEquals(1, entry.getExtensions().size());

    }

    public void testClone() throws Exception {
        AtomEntry entry = new AtomEntry();
        entry.getAuthors().add(new AtomPerson());
        entry.getCategories().add(new AtomCategory());
        entry.setContent(new AtomContent());
        entry.getContributors().add(new AtomPerson());
        entry.setId(new AtomId());
        entry.getLinks().add(new AtomLink());
        entry.setPublished(new AtomDate());
        entry.setRights(new AtomText());
        entry.setSource(new AtomSource());
        entry.setTitle(new AtomText());
        entry.setUpdated(new AtomDate());
        entry.getExtensions().add(new UnknownAtomExtension("a", "A"));

        assertEquals(entry, entry.clone());
    }

    public static void assertEquals(AtomEntry entry1, AtomEntry entry2) {
        if (notNull(entry1, entry2)) {
            TestAtomElement.assertEquals(entry1, entry2);

            assertListEquals(entry1.getAuthors(), entry2.getAuthors(), TestAtomPerson.class, AtomPerson.class);
            assertListEquals(entry1.getCategories(), entry2.getCategories(), TestAtomCategory.class,
                             AtomCategory.class);
            TestAtomContent.assertEquals(entry1.getContent(), entry2.getContent());
            assertListEquals(entry1.getContributors(), entry2.getContributors(), TestAtomPerson.class,
                             AtomPerson.class);
            TestAtomId.assertEquals(entry1.getId(), entry2.getId());
            assertListEquals(entry1.getLinks(), entry2.getLinks(), TestAtomLink.class, AtomLink.class);
            TestAtomDate.assertEquals(entry1.getPublished(), entry2.getPublished());
            TestAtomText.assertEquals(entry1.getRights(), entry2.getRights());
            TestAtomSource.assertEquals(entry1.getSource(), entry2.getSource());
            TestAtomText.assertEquals(entry1.getTitle(), entry2.getTitle());
            TestAtomDate.assertEquals(entry1.getUpdated(), entry2.getUpdated());

            TestExtensibleImpl.assertEquals(entry1, entry2);
        }
    }

}