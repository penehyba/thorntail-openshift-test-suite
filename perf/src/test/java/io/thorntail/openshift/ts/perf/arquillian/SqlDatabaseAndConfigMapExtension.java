package io.thorntail.openshift.ts.perf.arquillian;

import java.io.File;
import java.util.logging.Logger;

import io.fabric8.kubernetes.clnt.v4_0.KubernetesClient;
import io.fabric8.openshift.clnt.v4_0.OpenShiftClient;
import io.thorntail.openshift.ts.common.arquillian.OpenShiftUtil;
import org.arquillian.cube.kubernetes.impl.utils.CommandExecutor;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

public class SqlDatabaseAndConfigMapExtension implements LoadableExtension {
    @Override
    public void register(ExtensionBuilder builder) {
        builder.observer(SqlDatabaseAndConfigMapDeployer.class);
    }

    private static class SqlDatabaseAndConfigMapDeployer {

        @Inject
        private Instance<Injector> injector;

        @Inject
        private Instance<KubernetesClient> kubernetesClient;

        @Inject
        private Instance<OpenShiftUtil> openShiftUtil;

        protected final OpenShiftClient oc() {
            return kubernetesClient.get().adapt(OpenShiftClient.class);
        }

        protected final OpenShiftUtil openshift() {
            return openShiftUtil.get();
        }


        private static final Logger log = Logger.getLogger(SqlDatabaseAndConfigMapDeployer.class.getName());

        // precedence of these observers must be higher than the precedence of @OpenShiftResource observers
        // in Arquillian Cube, because the database must be deployed sooner / undeployed after the app
        // (which is deployed/undeployed using @OpenShiftResource)
        public void deploy(@Observes(precedence = 1000) BeforeClass event) throws Exception {
            new CommandExecutor().execCommand("oc", "project", oc().getNamespace());
            openshift().applyYaml(new File("target/test-classes/project-defaults.yml"));
        }

        public void undeploy(@Observes(precedence = 1000) AfterClass event) throws Exception {
        }
    }
}
