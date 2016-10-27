package com.util.xml;

import com.model.XmlReprezentationEmpls;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Artemie on 14.10.2016.
 */
public class XMLJAXBUtil {
    private JAXBContext jaxbContext = null;
    private Marshaller jaxbMarshaller = null;
    private XmlReprezentationEmpls empls = null;
    private File file = null;

    public XMLJAXBUtil(XmlReprezentationEmpls empls, String file) throws Exception {
        jaxbContext = JAXBContext.newInstance(XmlReprezentationEmpls.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.empls = empls;
        this.file = new File(file);
    }

    public void printConsole() throws Exception {
        jaxbMarshaller.marshal(this.empls, System.out);
    }

    public void printToFile() throws Exception {
        jaxbMarshaller.marshal(this.empls, this.file);
    }
}
