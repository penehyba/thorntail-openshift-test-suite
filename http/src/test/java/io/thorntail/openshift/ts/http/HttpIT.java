package io.thorntail.openshift.ts.http;

import org.arquillian.cube.openshift.api.OpenShiftResource;
import org.arquillian.cube.openshift.impl.enricher.AwaitRoute;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

// https://gitlab.cee.redhat.com/rhoar-qe/spring-boot-performance-test-app
// https://gitlab.cee.redhat.com/rhoar-qe/spring-boot-performance-tester
// AT FIRST deploy DB using ./initDB.sh

@RunWith(Arquillian.class)
//@SqlDatabaseAndConfigMap(Mysql.class)
@OpenShiftResource("file:target/classes/META-INF/fabric8/openshift.yml")
public class HttpIT {
    @RouteURL(value = "${app.name}", path = "/api/greeting")
    @AwaitRoute
    private String url;

//    @RouteURL(value = "${app.name}", path = "/api/exampleds")
//    @AwaitRoute
//    private String urlExampleDS;

    @RouteURL(value = "${app.name}", path = "/api/myds")
    @AwaitRoute
    private String urlMyDS;

//    @ArquillianResource
//    InitialContext context;

//    @Test
//    public void testDataSourceIsBound() throws Exception {
//        DataSource ds = (DataSource) context.lookup("java:jboss/datasources/MyDS");
//        assertNotNull(ds);
//    }

    @Test
    public void simpleInvocation() {
        given()
                .baseUri(url)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Hello, World!"));
    }

    @Test
    public void invocationWithParam() {
        given()
                .baseUri(url)
                .queryParam("name", "Peter")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Hello, Peter!"));
    }
//
//    @Test
//    public void exampleDS() {
//        given()
//                .baseUri(urlExampleDS)
//                .when()
//                .get()
//                .then()
//                .statusCode(200)
//                .body(containsString("some Example DS"));
//    }

    @Test
    public void myDS() {
        given()
                .baseUri(urlMyDS)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("34999"));
    }
}
