package org.jboss.additional.testsuite.jdkall.present.jaxrs.cache;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
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

