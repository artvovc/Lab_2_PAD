package com.util.xml;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

/**
 * Created by Artemie on 14.10.2016.
 */
public class XMLSAXUtil {

    private InputStream inputStream = null;
    private SAXParserFactory factory = null;
    private SAXParser saxParser = null;
    private ConcreteSAXParser concreteSAXParser = null;

    public XMLSAXUtil(InputStream is) throws Exception {
        inputStream = is;
        factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        concreteSAXParser = new ConcreteSAXParser();
    }

    public void parse() throws Exception {
        saxParser.parse(inputStream, concreteSAXParser);
    }

    public String getXml() {
        return concreteSAXParser.xml;
    }


    class ConcreteSAXParser extends DefaultHandler {
        private boolean bId = false;
        private boolean bFname = false;
        private boolean bLname = false;
        private boolean bAge = false;
        private boolean bSalary = false;
        private boolean bCreatedDate = false;

        String xml = "";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            xml += "<" + qName + ">";
            switch (qName) {
                case "id":
                    bId = true;
                    break;
                case "firstname":
                    bFname = true;
                    break;
                case "lastname":
                    bLname = true;
                    break;
                case "age":
                    bAge = true;
                    break;
                case "salary":
                    bSalary = true;
                    break;
                case "createdDate":
                    bCreatedDate = true;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            xml += "</" + qName + ">";
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (bId) {
//            xml += "<id>" + new String(ch, start, length) + "</id>";
                xml += new String(ch, start, length);
                bId = false;
            }
            if (bFname) {
//            xml += "<firstname>" + new String(ch, start, length) + "</firstname>";
                xml += new String(ch, start, length);
                bId = false;
            }
            if (bLname) {
//            xml += "<lastname>" + new String(ch, start, length) + "</lastname>";
                xml += new String(ch, start, length);
                bId = false;
            }
            if (bAge) {
//            xml += "<age>" + new String(ch, start, length) + "</age>";
                xml += new String(ch, start, length);
                bId = false;
            }
            if (bSalary) {
//            xml += "<salary>" + new String(ch, start, length) + "</salary>";
                xml += new String(ch, start, length);
                bId = false;
            }
            if (bCreatedDate) {
//            xml += "<createdDate>" + new String(ch, start, length) + "</createdDate>";
                xml += new String(ch, start, length);
                bId = false;
            }
        }
    }
}