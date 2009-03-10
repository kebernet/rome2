package net.java.rome2.atom;

public class TestAtomPerson extends BeanTestCase {

    public void testBean() {
        AtomPerson person = new AtomPerson();
        assertEquals(null, person.getName());
        assertEquals(null, person.getUri());
        assertEquals(null, person.getEmail());
        assertEquals(0, person.getExtensions().size());
        person.setName("n");
        person.setUri("u");
        person.setEmail("e");
        assertEquals("n", person.getName());
        assertEquals("u", person.getUri());
        assertEquals("e", person.getEmail());
    }

    public void testClone() throws Exception {
        AtomPerson person = new AtomPerson();
        person.setName("n");
        person.setUri("u");
        person.setEmail("e");
        person.getExtensions().add(new UnknownAtomExtension("a", "A"));
        assertEquals(person, person.clone());
    }

    public static void assertEquals(AtomPerson person1, AtomPerson person2) {
        if (notNull(person1, person2)) {
            TestAtomElement.assertEquals(person1, person2);
            assertEquals(person1.getName(), person2.getName());
            assertEquals(person1.getUri(), person2.getUri());
            assertEquals(person1.getEmail(), person2.getEmail());
            TestExtensibleImpl.assertEquals(person1, person2);
        }
    }
}