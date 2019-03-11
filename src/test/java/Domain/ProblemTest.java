package Domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProblemTest {
    private static final Long ID = new Long(1);
    private static final Long NewID = new Long(2);
    private static final String subject = "Web";
    private static final String difficulty = "Easy";
    private static final String text = "Blah blah";

    private Problem problem;

    @Before
    public void setUp() throws Exception {
        problem = new Problem(subject, difficulty, text);
        problem.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        problem = null;
    }

    @Test
    public void testGetId() {
        assertEquals(ID, problem.getId());
    }

    @Test
    public void testSetId() {
        problem.setId(NewID);
        assertEquals(NewID, problem.getId());
    }

    @Test
    public void testGetSubject() {
        assertEquals(problem.getSubject(), subject);
    }

    @Test
    public void testSetSubject() {
        problem.setSubject("Boo");
        assertEquals("Boo", problem.getSubject());
    }

    @Test
    public void testGetDiff() {
        assertEquals(problem.getDifficulty(), difficulty);
    }

    @Test
    public void testSetDiff() {
        problem.setDifficulty("EzPez");
        assertEquals("EzPez", problem.getDifficulty());
    }

    @Test
    public void testGetText() {
        assertEquals(text, problem.getText());
    }

    @Test
    public void testSetText() {
        problem.setText("Ehh");
        assertEquals("Ehh", problem.getText());
    }
}
