package vn.fpt.sims.iam.service.impl;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.config.ApplicationProperties;
import vn.fpt.sims.iam.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final Keycloak keycloak;

    @Autowired
    private final ApplicationProperties applicationProperties;

    private final ApplicationProperties.KeycloakClient keycloakProp;

    public GroupServiceImpl(Keycloak keycloak, ApplicationProperties applicationProperties) {
        this.keycloak = keycloak;
        this.applicationProperties = applicationProperties;
        this.keycloakProp = applicationProperties.getKeycloak();
    }

    @Override
    public void create(String name) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(name);
        keycloak
                .realm(keycloakProp.getRealm())
                .groups()
                .add(group);
    }

    @Override
    public List<GroupRepresentation> findAll() {
        return keycloak
                .realm(keycloakProp.getRealm())
                .groups()
                .groups();
    }
}
