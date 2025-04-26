package org.jboss.additional.testsuite.jdkall.present.jaxrs.atom;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.links.RESTServiceDiscovery;
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/jaxrs/src/main/java"})
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

