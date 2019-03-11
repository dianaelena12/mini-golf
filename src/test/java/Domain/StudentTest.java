package Domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    private static final Long ID = new Long(1);
    private static final Long NewID = new Long(2);
    private static final String SerialNr = "123r";
    private static final String NewSerialNr = "124r";
    private static final String Name = "Batman";
    private static final int Group = 925;

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student(SerialNr,Name,Group);
        student.setId(ID);
    }
    @After
    public void tearDown() throws Exception {
        student=null;
    }
    @Test
    public void testGenSerialNumber() throws Exception{
        assertEquals(SerialNr,student.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception{
        student.setSerialNumber(NewSerialNr);
        assertEquals(NewSerialNr,student.getSerialNumber());
    }

    @Test
    public void testGetId() throws Exception{
        assertEquals(ID, student.getId());
    }

    @Test
    public void testSetId() throws Exception{
        student.setId(NewID);
        assertEquals(NewID,student.getId());
    }
    @Test
    public void testGetName() throws Exception{
        assertEquals(Name,student.getName());
    }
    @Test
    public void testSetName() throws  Exception{
        student.setName("WonderWoman");
        assertEquals("WonderWoman",student.getName());
    }

    @Test
    public void testGetGroup() throws Exception{
        assertEquals(Group,student.getGroup());
    }

    @Test
    public void testSetGroup() throws Exception{
        student.setGroup(291);
        assertEquals(291,student.getGroup());
    }
}
