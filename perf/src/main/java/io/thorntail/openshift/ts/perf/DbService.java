package io.thorntail.openshift.ts.perf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import io.thorntail.openshift.ts.perf.model.*;

@ApplicationScoped
class DbService {

    private static final AtomicInteger insertCount = new AtomicInteger(0);

    private static final int LIMIT = 5;

    @Resource
    private DataSource myDS;

    public String checkMyDS(String query) {
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? String.valueOf(rs.getInt(1)) :
                    "MyDS: problem with resultSet, using connection: " + conn;
        } catch (SQLException e) {
            return "Exception occurred: " + e.getMessage();
        }
    }

    public String insertArticleByCompanyAndAuthor(Integer companyId, Integer authorId) throws SQLException {
        Company company = null;
        Person author = null;
        String content = "content of new article " + insertCount.getAndIncrement();
        try (Connection conn = myDS.getConnection()) {
            // load company
            PreparedStatement psCompany = conn.prepareStatement("SELECT id, name FROM company where id = " + companyId);
            ResultSet rsCompany = psCompany.executeQuery();
            if (rsCompany.next()) {
                company = new Company(rsCompany.getInt("id"), rsCompany.getString("name"));
            } else return "Unknown company id: " + companyId;
            // load author from person
            PreparedStatement psAuthor = conn.prepareStatement("SELECT id, firstname, lastname FROM person where id = " + authorId);
            ResultSet rsAuthor = psAuthor.executeQuery();
            if (rsAuthor.next()) {
                author = new Person(rsAuthor.getInt("id"), rsAuthor.getString("firstname"), rsAuthor.getString("lastname"));
            } else return "Unknown author id: " + authorId;

            Article article = new Article();
            article.setAuthor(author);
            article.setWrittenFor(company);
            article.setText(content);
            article.setPublished(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO article (author,writtenFor,text,published) " +
                            " VALUES (" + article.getAuthor().getId() + "," + article.getWrittenFor().getId() + "," +
                            "'" + article.getText() + "','" + article.getPublished() + "')");
            ps.execute();
        }
        return "Insert succeeded";
    }

    public List<Person> getPeopleWithPage(Integer page) throws SQLException {
        List<Person> people = new LinkedList<>();
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select p.id, p.firstname, p.lastname " +
                            "from person p " +
                            "limit " + page * LIMIT + "," + LIMIT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                people.add(new Person(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname")));
            }
            return people;
        }
    }

    public List<ArticleOutput> getArticlesWithPage(Integer companyId, Integer page) throws SQLException {
        List<ArticleOutput> articles = new LinkedList<>();
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select a.id, a.published, a.text, concat(p.firstname,' ',p.lastname) as author, c.name as writtenFor " +
                            "from article a, person p, company c " +
                            "where c.id = " + companyId + " and a.writtenFor = c.id and a.author = p.id " +
                            "limit " + page * LIMIT + "," + LIMIT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(new ArticleOutput(
                        rs.getInt("id"), rs.getString("published"), rs.getString("text"), rs.getString("author"), rs.getString("writtenFor")));
            }
            return articles;
        }
    }

    public List<ArticleOutput> getArticles(Integer companyId, Integer authorId) throws SQLException {
        List<ArticleOutput> articles = new LinkedList<>();
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select a.id, a.published, a.text, concat(p.firstname,' ',p.lastname) as author, c.name as writtenFor " +
                            "from article a, person p, company c " +
                            "where a.author = " + authorId + " and c.id = " + companyId + " and a.writtenFor = c.id and a.author = p.id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(new ArticleOutput(
                        rs.getInt("id"), rs.getString("published"), rs.getString("text"), rs.getString("author"), rs.getString("writtenFor")));
            }
            return articles;
        }
    }

    public List<ArticleOutput> getArticles(Integer authorId) throws SQLException {
        List<ArticleOutput> articles = new LinkedList<>();
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "select a.id, a.published, a.text, concat(p.firstname,' ',p.lastname) as author, c.name as writtenFor " +
                            "from article a, person p, company c " +
                            "where a.author = " + authorId + " and a.author = p.id and a.writtenFor = c.id limit " + LIMIT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(new ArticleOutput(
                        rs.getInt("id"), rs.getString("published"), rs.getString("text"), rs.getString("author"), rs.getString("writtenFor")));
            }
            return articles;
        }
    }
}
