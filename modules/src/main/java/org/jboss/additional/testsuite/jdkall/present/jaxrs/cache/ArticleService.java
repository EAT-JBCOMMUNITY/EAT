package org.jboss.additional.testsuite.jdkall.present.jaxrs.cache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.resteasy.annotations.cache.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/cacheservice")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
@EAT({"modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ArticleService {
    List<Article> list = new ArrayList<Article>();
    {
    	list.add(new Article("EAT", "Multiversion"));
    }

    @GET
    @Path("/articles")
    @Cache(maxAge = 1800, sMaxAge = 1000, mustRevalidate = true, proxyRevalidate = true)
    public Collection<Article> getArticles() {
       return list;
    }

    @POST
    @Path("/article")
    public void addArticle(Article article){
       list.add(article);	
    }

    @GET
    @Path("/article/{id}")
    public Article getArticle(@PathParam("id") Integer id) {
    	return list.get(id);
    }

    @PUT
    @Path("/article/{id}")
    public void updateArticle(@PathParam("id") Integer id, Article article){
    	list.set(id, article);
    }

    @DELETE
    @Path("/article/{id}")
    public void deleteArticle(@PathParam("id") Integer id){
    	list.remove(id);
    }
}
