package io.thorntail.openshift.ts.http;

import java.io.IOException;
import java.net.URL;

import io.dekorate.testing.annotation.Inject;
import io.dekorate.testing.openshift.annotation.OpenshiftIntegrationTest;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.LocalPortForward;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import io.fabric8.kubernetes.client.Openshift;

@OpenshiftIntegrationTest
public class ThorntailOnOpenshiftIT {
    @Inject
    private KubernetesClient client;
//    private OpenshiftClient client;

//    @Inject
//    private KubernetesList list;
//    private OpenshiftList list;

    @Inject
    private Pod pod;

    @Test
    void shouldRespondWithHelloWorld() throws IOException {
        System.out.println("Starting shouldRespondWithHelloWorld()...");
        assertNotNull(client);
        System.out.println("KubernetesClient is not null.");
//        assertNotNull(list);
        try (LocalPortForward p = client.pods().withName(pod.getMetadata().getName()).portForward(8080)) {
            assertTrue(p.isAlive());

            URL url = new URL("http://localhost:" + p.getLocalPort() + "/");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(url).build();
            Response response = client.newCall(request).execute();
            assertEquals("Hello world", response.body().string());
        }
    }
}