package de.krayadev.taskitory.iamConnector;

import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.spi.HttpFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.keycloak.adapters.KeycloakDeployment;

@Configuration
public class TaskitoryKeycloakConfigResolver extends KeycloakSpringBootConfigResolver {

    private final KeycloakDeployment keycloakDeployment;

    @Autowired
    TaskitoryKeycloakConfigResolver(final KeycloakSpringBootProperties properties){
        this.keycloakDeployment = KeycloakDeploymentBuilder.build(properties);
    }

    @Override
    public KeycloakDeployment resolve(final HttpFacade.Request request) {
        return this.keycloakDeployment;
    }

}
