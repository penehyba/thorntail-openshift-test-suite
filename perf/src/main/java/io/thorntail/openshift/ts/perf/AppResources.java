package io.thorntail.openshift.ts.perf;


import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.thorntail.openshift.ts.perf.model.ArticleOutput;
import io.thorntail.openshift.ts.perf.model.Person;

@Path("/")
@ApplicationScoped
@Produces("application/json")
public class AppResources {

    @Inject
    private DbService repo;

    @GET
    @Path("ping")
    public String ping() {
        return "pong";
    }

    @GET
    @Path("myds")
    public String testDataSourceIsBound() {
        return repo.checkMyDS("select 1");
    }

    @GET
    @Path("myds/article")
    public String testLoadedDB() {
        return repo.checkMyDS("select id,author,writtenFor,published from article where id = 34999");
    }

    @GET
    @Path("article/author/{authorId}")
    public List<ArticleOutput> fromAuthorGet(@DefaultValue("1") @PathParam("authorId") Integer authorId) throws Exception {
        return repo.getArticles(authorId);
    }

    @POST
    @Path("article/author")
    public List<ArticleOutput> fromAuthor(@DefaultValue("1") @HeaderParam("author") Integer authorId) throws Exception {
        return repo.getArticles(authorId);
    }

    @GET
    @Path("article/company/author/{companyId}/{authorId}")
    public List<ArticleOutput> fromAuthorForCompanyGet(@PathParam("companyId") Integer companyId, @PathParam("authorId") Integer authorId) throws Exception {
        return repo.getArticles(companyId, authorId);
    }

    @POST
    @Path("article/company/author")
    public List<ArticleOutput> fromAuthorForCompany(@HeaderParam("company") Integer companyId, @HeaderParam("author") Integer authorId) throws Exception {
        return repo.getArticles(companyId, authorId);
    }

    @GET
    @Path("article/company/page/{companyId}/{page}")
    public List<ArticleOutput> fromCompanyGet(@PathParam("companyId") Integer companyId, @PathParam("page") Integer page) throws Exception {
        return repo.getArticlesWithPage(companyId, page);
    }

    @POST
    @Path("article/company/page")
    public List<ArticleOutput> fromCompany(@HeaderParam("company") Integer companyId, @HeaderParam("page") Integer page) throws Exception {
        return repo.getArticlesWithPage(companyId, page);
    }

    @GET
    @Path("person/page/{page}")
    public List<Person> personPageGet(@PathParam("page") Integer page) throws Exception {
        return repo.getPeopleWithPage(page);
    }

    @POST
    @Path("person/page")
    public List<Person> fromCompany(@HeaderParam("page") Integer page) throws Exception {
        return repo.getPeopleWithPage(page);
    }

    @GET
    @Path("insert/article/{companyId}/{authorId}")
    public String insertArticleGet(@PathParam("companyId") Integer companyId, @PathParam("authorId") Integer authorId) throws SQLException {
        return repo.insertArticleByCompanyAndAuthor(companyId, authorId);
    }

    @POST
    @Path("insert/article")
    public String insertArticle(@DefaultValue("1") @HeaderParam("company") Integer companyId, @DefaultValue("1") @HeaderParam("author") Integer authorId) throws SQLException {
        return repo.insertArticleByCompanyAndAuthor(companyId, authorId);
    }
}