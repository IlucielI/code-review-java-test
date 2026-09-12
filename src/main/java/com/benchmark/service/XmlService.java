package com.benchmark.service;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlService {
    // XXE vulnerability: DocumentBuilderFactory without disallowing DOCTYPE or external DTDs
    public Document parseXml(String untrustedXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(untrustedXml)));
    }
}
