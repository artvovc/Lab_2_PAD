package com.util.xml;

import com.model.Empl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artemie on 14.10.2016.
 */

public final class XMLDOMUtil {
    DocumentBuilderFactory docFactory;
    DocumentBuilder documentBuilder;
    Document doc;
    List<Empl> empls;

    public XMLDOMUtil(List<Empl> empls) throws ParserConfigurationException {
        docFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = docFactory.newDocumentBuilder();
        doc = documentBuilder.newDocument();
        this.empls = empls;
    }

    private void appendSimpleNode(Element root, String nameNode, Object value) {
        Element element = doc.createElement(nameNode);
        element.appendChild(doc.createTextNode((String)value));
        root.appendChild(element);
    }

    private void appendComplexListNode(Element root, String nameNode, List<Empl> values) {
        values.forEach(empl -> {
            Element listElement = doc.createElement(nameNode);
            appendSimpleNode(listElement,"id",empl.getId().toString());
            appendSimpleNode(listElement,"firstname",empl.getFirstname());
            appendSimpleNode(listElement,"lastname",empl.getLastname());
            appendSimpleNode(listElement,"age",empl.getAge().toString());
            appendSimpleNode(listElement,"salary",empl.getSalary().toString());
            appendSimpleNode(listElement,"createdDate",empl.getCreatedDate().toString());
            root.appendChild(listElement);
        });

    }

    public Document createXMLDoc() {
        Element root = doc.createElement("empls");
        doc.appendChild(root);
        appendComplexListNode(root,"empl",this.empls);
        return doc;
    }

    public List<Empl> createListFromDoc(Document doc){
        this.empls = new ArrayList<Empl>();

//        Element elem = doc.getElementById("empls");

        NodeList nodes = doc.getElementsByTagName("empl");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node tempNode = nodes.item(i);
            Element tempElement = (Element) tempNode;
            Empl tempEmpl = new Empl();

            tempEmpl.setId(Integer.parseInt(tempElement.getElementsByTagName("id").item(0).getTextContent()));
            tempEmpl.setFirstname(tempElement.getElementsByTagName("firstname").item(0).getTextContent());
            tempEmpl.setLastname(tempElement.getElementsByTagName("lastname").item(0).getTextContent());
            tempEmpl.setAge(Integer.parseInt(tempElement.getElementsByTagName("age").item(0).getTextContent()));
            tempEmpl.setSalary(Integer.parseInt(tempElement.getElementsByTagName("salary").item(0).getTextContent()));
            tempEmpl.setCreatedDate(Long.parseLong(tempElement.getElementsByTagName("createdDate").item(0).getTextContent()));

            this.empls.add(tempEmpl);
        }
        return this.empls;
    }

}
