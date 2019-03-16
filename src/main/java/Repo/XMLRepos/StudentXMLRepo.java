package Repo.XMLRepos;

import Domain.Assignment;
import Domain.Student;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoEntityStored;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repo.InMemRepo;
import javax.xml.transform.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.Optional;

public class StudentXMLRepo extends InMemRepo<Long, Student> {
    public StudentXMLRepo(Validator<Student> validator) {
        super(validator);
        try {
            loadData();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void loadData() throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml");
        Element root = doc.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();
        for(int i = 0; i < len; i++) {
            Node studentNode = nodes.item(i);
            if(studentNode instanceof Element) {
                Student student = createStudent((Element) studentNode);
                super.save(student);
            }
        }
    }

    private static  Student createStudent(Element studentNode) {
        String id = studentNode.getAttribute("id");
        Student student = new Student();
        student.setId(Long.valueOf(id).longValue());

        student.setName(getTextFromTagName(studentNode, "name"));
        student.setSerialNumber(getTextFromTagName(studentNode, "serial-number"));
        student.setGroup(Integer.parseInt(getTextFromTagName(studentNode, "group")));

        return student;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    private static void saveToXML(Student student) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml");
        Element root = doc.getDocumentElement();
        Element studentElement = doc.createElement("student");

        studentElement.setAttribute("id", student.getId().toString());
        root.appendChild(studentElement);

        appendChildWithText(doc, studentElement, "name", student.getName());
        appendChildWithText(doc, studentElement, "serial-number", student.getSerialNumber());
        appendChildWithText(doc, studentElement, "group", "" + student.getGroup());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml")));
}

    private static void deleteFromXML(Long id) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml");

        NodeList list = doc.getElementsByTagName("student");
        for(int i = 0; i < list.getLength(); i++) {
            Element element = (Element)list.item(i);
            Long nr = Long.valueOf(element.getAttribute("id")).longValue();
            if(nr.longValue() == id){
                element.getParentNode().removeChild(element);
            }
        }

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml")));
    }

    private static void updateXML(Student student) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml");

        NodeList list = doc.getElementsByTagName("student");
        for(int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            Long id = Long.valueOf(element.getAttribute("id")).longValue();
            if (id.longValue() == student.getId()) {
                element.getParentNode().removeChild(element);
                Element root = doc.getDocumentElement();
                Element studentElement = doc.createElement("student");

                studentElement.setAttribute("id", student.getId().toString());
                root.appendChild(studentElement);

                appendChildWithText(doc, studentElement, "name", student.getName());
                appendChildWithText(doc, studentElement, "serial-number", student.getSerialNumber());
                appendChildWithText(doc, studentElement, "group", "" + student.getGroup());
                }
            }

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students.xml")));
    }

    public Optional<Student> save(Student entity) throws ValidatorException{
        if(findOne(entity.getId()).isPresent()){
            throw new DuplicateException("There can't be two students with the same id!");
        }
        Optional<Student> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveToXML(entity);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        try{
            deleteFromXML(id);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        Optional<Student> optional = super.delete(id);
        if(findOne(id).isPresent()){
            throw new NoEntityStored("Assignment does not exist in the database!");
        }
        return optional;
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {
        try{
            updateXML(entity);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        Optional<Student> optional = super.update(entity);
        if(!findOne(entity.getId()).isPresent()){
            throw new NoEntityStored("Student does not exist in the database!");
        }
        return optional;
    }

    private static void appendChildWithText(Document doc, Node parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }
}
