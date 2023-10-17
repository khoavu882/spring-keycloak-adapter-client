package vn.fpt.sims.iam.service.impl;

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

    @Autowired
    private final ApplicationProperties applicationProperties;

    public RoleServiceImpl(Keycloak keycloak, ApplicationProperties applicationProperties) {
        this.keycloak = keycloak;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void create(String name) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(name);
        keycloak.realm(applicationProperties.getKeycloak().getRealm()).roles().create(role);
    }

    @Override
    public List<RoleRepresentation> findAll() {
        return keycloak.realm(applicationProperties.getKeycloak().getRealm()).roles().list();
    }

    @Override
    public RoleRepresentation findByName(String roleName) {
        return keycloak.realm(applicationProperties.getKeycloak().getRealm()).roles().get(roleName).toRepresentation();
    }
}
