package org.jboss.additional.testsuite.jdkall.present.jaxrs.atom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.ws.rs.Consumes;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
 
@Path("/articleservice")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#27.0.0.Alpha4"})
public class ArticleService {
    List<Article> list = new ArrayList<Article>();
    {
    	list.add(new Article("EAT", "Multiversion"));
    }
    @AddLinks
    @LinkResource(value = Article.class)
    @GET
    @Path("/articles")
    public Collection<Article> getArticles() {
       return list;
    }
    @LinkResource
    @POST
    @Path("/article")
    public void addArticle(Article article){
       list.add(article);	
    }
    @AddLinks
    @LinkResource
    @GET
    @Path("/article/{id}")
    public Article getArticle(@PathParam("id") Integer id) {
    	return list.get(id);
    }
    @LinkResource
    @PUT
    @Path("/article/{id}")
    public void updateArticle(@PathParam("id") Integer id, Article article){
    	list.set(id, article);
    }
    @LinkResource(value = Article.class)
    @DELETE
    @Path("/article/{id}")
    public void deleteArticle(@PathParam("id") Integer id){
    	list.remove(id);
    }
}
