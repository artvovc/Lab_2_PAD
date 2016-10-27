package com.util.xml;

import com.model.Empl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
//        root.appendChild(doc.createElement(nameNode).appendChild(doc.createTextNode((String) value)));
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

    public List<Empl> getEmpls() {
        return empls;
    }
}
