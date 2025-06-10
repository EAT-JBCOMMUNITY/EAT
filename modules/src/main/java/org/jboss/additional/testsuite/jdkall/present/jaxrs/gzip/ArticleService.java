package org.jboss.additional.testsuite.jdkall.present.jaxrs.gzip;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.jboss.resteasy.annotations.GZIP;

@Path("/gzipservice")
@EAT({"modules/testcases/jdkAll/Eap73x/jaxrs/src/main/java","modules/testcases/jdkAll/Eap7Plus/jaxrs/src/main/java"})
public class ArticleService {
    List<Article> list = new ArrayList<Article>();
    {
    	list.add(new Article("EAT", "Multiversion"));
    }

    @GET
    @GZIP
    @Path("/articles")
    public Collection<Article> getArticles() {
       return list;
    }

    @POST
    @GZIP
    @Path("/article")
    public void addArticle(Article article){
       list.add(article);	
    }

    @GET
    @Path("/article/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Article getArticle(@PathParam("id") Integer id) {
    	return list.get(id);
    }

    @PUT
    @Path("/article/{id}")
    public void updateArticle(@PathParam("id") Integer id, @GZIP Article article){
    	list.set(id, article);
    }

    @DELETE
    @Path("/article/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public void deleteArticle(@PathParam("id") Integer id){
    	list.remove(id);
    }

    @GET
    @Path("/a")
    @Compress
    public String article() {
       return "EAT";
    }
}
