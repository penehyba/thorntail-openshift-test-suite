package io.thorntail.openshift.ts.http;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.openshift.annotation.OpenshiftApplication;
import io.dekorate.option.annotation.JvmOptions;
import io.dekorate.option.annotation.SecureRandomSource;

@OpenshiftApplication(
        livenessProbe = @Probe(httpActionPath = "/health", initialDelaySeconds = 180),
        readinessProbe = @Probe(httpActionPath = "/health", initialDelaySeconds = 20),
        expose = true
)
@JvmOptions(server = true, preferIPv4Stack = true, secureRandom = SecureRandomSource.NonBlocking)
@ApplicationPath("/")
public class RestApplication extends Application {
}