package Repo.XMLRepos;

import Domain.Problem;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoEntityStored;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repo.InMemRepo;
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

public class ProblemXMLRepo extends InMemRepo<Long,Problem> {
    public ProblemXMLRepo(Validator<Problem> validator) {
        super(validator);
        try {
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Problem createProblem(Element ProblemNode) {
        String id = ProblemNode.getAttribute("id");
        Problem Problem = new Problem();
        Problem.setId(Long.valueOf(id).longValue());

        Problem.setSubject(getTextFromTagName(ProblemNode, "subject"));
        Problem.setDifficulty(getTextFromTagName(ProblemNode, "difficulty"));
        Problem.setText(getTextFromTagName(ProblemNode, "text"));

        return Problem;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    private static void saveToXML(Problem Problem) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml");
        Element root = doc.getDocumentElement();
        Element ProblemElement = doc.createElement("Problem");

        ProblemElement.setAttribute("id", Problem.getId().toString());
        root.appendChild(ProblemElement);

        appendChildWithText(doc, ProblemElement, "subject", Problem.getSubject());
        appendChildWithText(doc, ProblemElement, "difficulty", Problem.getDifficulty());
        appendChildWithText(doc, ProblemElement, "text",  Problem.getText());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml")));
    }

    private static void deleteFromXML(Long id) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml");

        NodeList list = doc.getElementsByTagName("Problem");
        AssignmentXMLRepo.removeChildFromNode(id, list);

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml")));
    }

    private static void updateXML(Problem Problem) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml");

        NodeList list = doc.getElementsByTagName("Problem");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            Long id = Long.valueOf(element.getAttribute("id")).longValue();
            if (id.longValue() == Problem.getId()) {
                element.getParentNode().removeChild(element);
                Element root = doc.getDocumentElement();
                Element ProblemElement = doc.createElement("Problem");

                ProblemElement.setAttribute("id", Problem.getId().toString());
                root.appendChild(ProblemElement);

                appendChildWithText(doc, ProblemElement, "subject", Problem.getSubject());
                appendChildWithText(doc, ProblemElement, "difficulty", Problem.getDifficulty());
                appendChildWithText(doc, ProblemElement, "text", "" + Problem.getText());
            }
        }

        Element root = doc.getDocumentElement();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult(new
                FileOutputStream("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml")));
    }

    private static void appendChildWithText(Document doc, Node parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private void loadData() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse("C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems.xml");
        Element root = doc.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        int len = nodes.getLength();
        for (int i = 0; i < len; i++) {
            Node ProblemNode = nodes.item(i);
            if (ProblemNode instanceof Element) {
                Problem Problem = createProblem((Element) ProblemNode);
                super.save(Problem);
            }
        }
    }

    public Optional<Problem> save(Problem entity) throws ValidatorException {
        if (findOne(entity.getId()).isPresent()) {
            throw new DuplicateException("There can't be two Problems with the same id!");
        }
        Optional<Problem> optional = super.save(entity);
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
    public Optional<Problem> delete(Long id) {
        try {
            deleteFromXML(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Optional<Problem> optional = super.delete(id);
        if (findOne(id).isPresent()) {
            throw new NoEntityStored("Problem does not exist in the database!");
        }
        return optional;
    }

    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException {
        try {
            updateXML(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Optional<Problem> optional = super.update(entity);
        if (!findOne(entity.getId()).isPresent()) {
            throw new NoEntityStored("Problem does not exist in the database!");
        }
        return optional;
    }
}
