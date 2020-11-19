//package io.thorntail.openshift.ts.http;
//
//import java.io.IOException;
//
//import io.dekorate.testing.annotation.Inject;
//import io.dekorate.testing.openshift.annotation.OpenshiftIntegrationTest;
//import io.fabric8.kubernetes.api.model.KubernetesList;
//import io.fabric8.kubernetes.client.KubernetesClient;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
////@RunWith(Arquillian.class)
//@OpenshiftIntegrationTest
//public class HttpIT {
//    //    @RouteURL(value = "${app.name}", path = "/api/greeting")
////    @AwaitRoute
////    private String url;
//    @Inject
//    private KubernetesClient client;
//
//    @Inject
//    private KubernetesList list;
//
//    @Test
//    void shouldRespondWithHelloWorld() throws IOException {
//        System.out.println("shouldRespondWithHelloWorld() starting..");
//        assertNotNull(client);
//        assertNotNull(list);
//    }
////    @Test
////    public void simpleInvocation() {
////        given()
////                .baseUri(url)
////        .when()
////                .get()
////        .then()
////                .statusCode(200)
////                .body(containsString("Hello, World!"));
////    }
////
////    @Test
////    public void invocationWithParam() {
////        given()
////                .baseUri(url)
////                .queryParam("name", "Peter")
////        .when()
////                .get()
////        .then()
////                .statusCode(200)
////                .body(containsString("Hello, Peter!"));
////    }
//}
