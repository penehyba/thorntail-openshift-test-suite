package io.thorntail.openshift.ts.http;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.thorntail.openshift.ts.http.model.Company;
import io.thorntail.openshift.ts.http.model.Person;

@Path("/")
@ApplicationScoped
public class GreetingResource {

    private static final String TEMPLATE = "Hello, %s!";

    private static final String SELECT_1 = "Select 1";

    @Resource
    private DataSource myDS;

    @GET
    @Path("ping")
    public String ping() {
        return "pong";
    }

    @GET
    @Path("company")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getAll() {
        List<Company> companies = new LinkedList<>();
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement psCompany = conn.prepareStatement("SELECT id, name FROM company");
            ResultSet rsCompany = psCompany.executeQuery();
            while (rsCompany.next()) {
                companies.add(new Company(rsCompany.getInt("id"), rsCompany.getString("name")));
            }
            for (Company c : companies) {
                Set<Person> employees = new HashSet<>();
                PreparedStatement ps = conn.prepareStatement(
                        " select" +
                                "        employees0_.companyId as companyId," +
                                "        employees0_.personId as personId," +
                                "        person1_.id as id," +
                                "        person1_.firstname as firstname," +
                                "        person1_.lastname as lastname" +
                                "    from" +
                                "        employee employees0_ " +
                                "    inner join" +
                                "        person person1_ " +
                                "            on employees0_.personId=person1_.id " +
                                "    where" +
                                "        employees0_.companyId=" + c.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    employees.add(new Person(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname")));
                }
                c.setEmployees(employees);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return null;
        }
        return companies;
    }

    @GET
    @Path("greeting")
    @Produces("application/json")
    public Greeting greeting(@QueryParam("name") @DefaultValue("World") String name) {
        return new Greeting(String.format(TEMPLATE, name));
    }

//    @GET
//    @Path("exampleds")
//    public String testExampleDataSourceIsBound() {
//        try {
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("java:jboss/datasources/ExampleDS");
//            try (Connection conn = ds.getConnection()) {
//                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_1);
//                ResultSet rs = preparedStatement.executeQuery();
//                return rs.next() ? String.valueOf(rs.getInt(1)) :
//                        "ExampleDS: problem with resultSet, using connection: " + conn;
//            }
//        } catch (Exception e) {
//            return "Exception occurred: " + e.getMessage();
//        }
//    }

    @GET
    @Path("myds")
    public String testDataSourceIsBound() {
        try (Connection conn = myDS.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("select count(*) from article");
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? String.valueOf(rs.getInt(1)) :
                    "MyDS: problem with resultSet, using connection: " + conn;
        } catch (SQLException e) {
            return "Exception occurred: " + e.getMessage();
        }

//        try {
//            Context ctx = new InitialContext();
//            myDS = (DataSource) ctx.lookup("java:jboss/datasources/MyDS");
//            return myDS != null ? "DataSource not null: " + myDS.toString() : "NULL DataSource!";
//        } catch (Exception e) {
//            return "Exception occurred: " + e.getMessage();
//        }
    }
}
