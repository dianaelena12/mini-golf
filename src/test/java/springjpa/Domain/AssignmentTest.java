package springjpa.Domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {

    private static final Long ID = new Long(1);
    private static final Long NewID = new Long(2);
    private static final Long studId = new Long(1);
    private static final Long probId = new Long(1);
    private static final int grade = 0;

    Assignment assignment;

    @Before
    public void setUp() throws Exception {
        assignment = new Assignment(studId, probId, grade);
        assignment.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        assignment = null;
    }

    @Test
    public void testGetId() {
        assertEquals(ID, assignment.getId());
    }

    @Test
    public void testSetId() {
        assignment.setId(NewID);
        assertEquals(NewID, assignment.getId());
    }

    @Test
    public void testGetStudId() {
        assertEquals(assignment.getStudentID(), studId);
    }

    @Test
    public void testSetStudId() {
        assignment.setStudentID(new Long(2));
        assertEquals(new Long(2), assignment.getStudentID());
    }

    @Test
    public void testGetProbId() {
        assertEquals(assignment.getProblemID(), probId);
    }

    @Test
    public void testSetProbId() {
        assignment.setProblemID(new Long(2));
        assertEquals(new Long(2), assignment.getProblemID());
    }

    @Test
    public void testGetGrade() {
        assertEquals(grade, assignment.getGrade());
    }

    @Test
    public void testSetText() {
        assignment.setGrade(10);
        assertEquals(10, assignment.getGrade());
    }
}
