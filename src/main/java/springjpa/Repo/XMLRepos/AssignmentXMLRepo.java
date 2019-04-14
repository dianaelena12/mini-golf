package springjpa.Repo.XMLRepos;

import springjpa.Domain.Assignment;
import springjpa.Domain.Validators.DuplicateException;
import springjpa.Domain.Validators.NoEntityStored;
import springjpa.Domain.Validators.Validator;
import springjpa.Domain.Validators.ValidatorException;
import springjpa.Repo.InMemRepo;
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
import java.util.Optional;

public class AssignmentXMLRepo extends InMemRepo<Long, Assignment> {
    public AssignmentXMLRepo(Validator<Assignment> validator) {
        super(validator);
        try {
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Assignment createAssignment(Element AssignmentNode) {
        String id = AssignmentNode.getAttribute("id");
        Assignment Assignment = new Assignment();
        Assignment.setId(Long.valueOf(id).longValue());

        Assignment.setStudentID(Long.valueOf((getTextFromTagName(AssignmentNode, "studentID"))));
        Assignment.setProblemID(Long.valueOf(getTextFromTagName(AssignmentNode, "problemID")));
        Assignment.setGrade(Integer.parseInt(getTextFromTagName(AssignmentNode, "grade")));

        return Assignment;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    private static void saveToXML(Assignment Assignment) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml");
        Element root = doc.getDocumentElement();
        Element AssignmentElement = doc.createElement("Assignment");

        AssignmentElement.setAttribute("id", Assignment.getId().toString());
        root.appendChild(AssignmentElement);

        appendChildWithText(doc, AssignmentElement, "studentID", "" + Assignment.getStudentID());
        appendChildWithText(doc, AssignmentElement, "problemID", "" + Assignment.getProblemID());
        appendChildWithText(doc, AssignmentElement, "grade", "" + Assignment.getGrade());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml")));
    }

    private static void deleteFromXML(Long id) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml");

        NodeList list = doc.getElementsByTagName("Assignment");
        removeChildFromNode(id, list);

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml")));
    }

    static void removeChildFromNode(Long id, NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            Long nr = Long.valueOf(element.getAttribute("id")).longValue();
            if (nr.longValue() == id) {
                element.getParentNode().removeChild(element);
            }
        }
    }

    private static void updateXML(Assignment Assignment) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml");

        NodeList list = doc.getElementsByTagName("Assignment");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            Long id = Long.valueOf(element.getAttribute("id")).longValue();
            if (id.longValue() == Assignment.getId()) {
                element.getParentNode().removeChild(element);
                Element root = doc.getDocumentElement();
                Element AssignmentElement = doc.createElement("Assignment");

                AssignmentElement.setAttribute("id", Assignment.getId().toString());
                root.appendChild(AssignmentElement);

                appendChildWithText(doc, AssignmentElement, "studentID", "" + Assignment.getStudentID());
                appendChildWithText(doc, AssignmentElement, "problemID", "" + Assignment.getProblemID());
                appendChildWithText(doc, AssignmentElement, "grade", "" + Assignment.getGrade());
            }
        }

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml")));
    }

    private static void appendChildWithText(Document doc, Node parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private void loadData() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments.xml");
        Element root = doc.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();
        for (int i = 0; i < len; i++) {
            Node AssignmentNode = nodes.item(i);
            if (AssignmentNode instanceof Element) {
                Assignment Assignment = createAssignment((Element) AssignmentNode);
                super.save(Assignment);
            }
        }
    }

    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        if (findOne(entity.getId()).isPresent()) {
            throw new DuplicateException("There can't be two Assignments with the same id!");
        }
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveToXML(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        try {
            deleteFromXML(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Optional<Assignment> optional = super.delete(id);
        if (findOne(id).isPresent()) {
            throw new NoEntityStored("Assignment does not exist in the database!");
        }
        return optional;
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException {
        try {
            updateXML(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Optional<Assignment> optional = super.update(entity);
        if (!findOne(entity.getId()).isPresent()) {
            throw new NoEntityStored("Assignment does not exist in the database!");
        }
        return optional;
    }
}
