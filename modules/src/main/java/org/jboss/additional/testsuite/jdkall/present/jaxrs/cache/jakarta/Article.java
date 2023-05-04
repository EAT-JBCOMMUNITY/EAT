package org.jboss.additional.testsuite.jdkall.present.jaxrs.cache;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4"})
public class Article {
    @XmlAttribute
    private String author;
    @XmlID
    @XmlAttribute	
    private String title;

    public Article(){}
    public Article(String author, String title){
    	this.author = author;
    	this.title = title;
    }
}

