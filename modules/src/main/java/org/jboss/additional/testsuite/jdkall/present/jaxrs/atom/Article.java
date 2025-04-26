package org.jboss.additional.testsuite.jdkall.present.jaxrs.atom;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.links.RESTServiceDiscovery;
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class Article {
    @XmlAttribute
    private String author;
    @XmlID
    @XmlAttribute	
    private String title;
    @XmlElementRef
    private RESTServiceDiscovery rest;
    public Article(){}
    public Article(String author, String title){
    	this.author = author;
    	this.title = title;
    }
}

