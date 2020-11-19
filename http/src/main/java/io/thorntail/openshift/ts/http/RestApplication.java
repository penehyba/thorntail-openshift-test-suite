package io.thorntail.openshift.ts.http;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.dekorate.openshift.annotation.OpenshiftApplication;

@ApplicationPath("/api")
@OpenshiftApplication(expose=true)
public class RestApplication extends Application {
}
