package vn.fpt.sims.iam.service.impl;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.config.ApplicationProperties;
import vn.fpt.sims.iam.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final Keycloak keycloak;

    private final KeycloakSpringBootProperties keycloakProp;

    public RoleServiceImpl(Keycloak keycloak, KeycloakSpringBootProperties keycloakProp) {
        this.keycloak = keycloak;
        this.keycloakProp = keycloakProp;
    }

    @Override
    public void create(String name) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(name);
        keycloak.realm(keycloakProp.getRealm()).roles().create(role);
    }

    @Override
    public List<RoleRepresentation> findAll() {
        return keycloak.realm(keycloakProp.getRealm()).roles().list();
    }

    @Override
    public RoleRepresentation findByName(String roleName) {
        return keycloak.realm(keycloakProp.getRealm()).roles().get(roleName).toRepresentation();
    }
}
