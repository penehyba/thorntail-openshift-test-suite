package io.thorntail.openshift.ts.perf;

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
// then: mvn clean install -B -fae  -pl common,perf

@RunWith(Arquillian.class)
@OpenShiftResource("file:target/classes/META-INF/fabric8/openshift.yml")
public class PerformanceIT {
    @RouteURL(value = "${app.name}", path = "/ping")
    @AwaitRoute
    private String urlPing;

    @RouteURL(value = "${app.name}", path = "/myds")
    @AwaitRoute
    private String urlMyDS;

    @RouteURL(value = "${app.name}", path = "/myds/article")
    @AwaitRoute
    private String urlMyDSArticle;

    @RouteURL(value = "${app.name}", path = "/article/author/1")
    private String urlArticleAuthor_1;

    @RouteURL(value = "${app.name}", path = "/article/company/author/1/1")
    private String urlArticleCompanyAuthor_1_1;

    @RouteURL(value = "${app.name}", path = "/article/company/page/1/2")
    private String urlArticleCompanyPage_1_2;

    @RouteURL(value = "${app.name}", path = "/person/page/2")
    private String urlPersonPage_2;

    @RouteURL(value = "${app.name}", path = "/insert/article/5/7")
    private String urlInsertArticle_5_7;

    @Test
    public void ping() {
        given()
                .baseUri(urlPing)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("pong"));
    }

    @Test
    public void dataSourceReady() {
        given()
                .baseUri(urlMyDS)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("1"));
    }

    @Test
    public void databaseLoaded() {
        given()
                .baseUri(urlMyDSArticle)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("34999"));
    }

    @Test
    public void fromAuthor() {
        given()
                .baseUri(urlArticleAuthor_1)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Bilbo Baggins"));
    }

    @Test
    public void fromAuthorForCompany() {
        given()
                .baseUri(urlArticleCompanyAuthor_1_1)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Fellowship of the ring"));
    }

    @Test
    public void fromCompanyPage() {
        given()
                .baseUri(urlArticleCompanyPage_1_2)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Geraldine Ramirez"));
    }

    @Test
    public void fromPersonPage() {
        given()
                .baseUri(urlPersonPage_2)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Amaya"));
    }

    @Test
    public void insertArticle() {
        given()
                .baseUri(urlInsertArticle_5_7)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body(containsString("Insert succeeded"));
    }
}
