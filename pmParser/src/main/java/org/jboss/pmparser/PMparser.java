/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.pmparser;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author panos
 */
public class PMparser {

    public static void main(String[] args) {
        System.setProperty("ProvisionedXmlPath", "/home/panos/pm-test/.pm");
        String filePath = System.getProperty("ProvisionedXmlPath");

        try {
            File inputFile = new File(filePath + "/provisioned.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("config");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("config name : "
                            + eElement.getAttribute("name"));
                    NodeList nList2 = eElement.getElementsByTagName("spec");

                    for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                        Node nNode2 = nList2.item(temp2);
                        System.out.println("\nCurrent Element :" + nNode2.getNodeName());

                        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement2 = (Element) nNode2;
                            System.out.println("spec name : "
                                    + eElement2.getAttribute("name"));
                            NodeList nList3 = eElement2.getElementsByTagName("feature");

                            for (int temp3 = 0; temp3 < nList3.getLength(); temp3++) {
                                Node nNode3 = nList3.item(temp3);
                                System.out.println("\nCurrent Element :" + nNode3.getNodeName());

                                if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement3 = (Element) nNode3;
                                    System.out.println("feature id : "
                                            + eElement3.getAttribute("id"));
                                }
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
