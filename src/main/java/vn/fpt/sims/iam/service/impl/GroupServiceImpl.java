package vn.fpt.sims.iam.service.impl;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final Keycloak keycloak;

    private final KeycloakSpringBootProperties keycloakProp;

    public GroupServiceImpl(Keycloak keycloak, KeycloakSpringBootProperties keycloakProp) {
        this.keycloak = keycloak;
        this.keycloakProp = keycloakProp;
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
