package vn.fpt.sims.iam.service.impl;

import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.GroupService;
import vn.fpt.sims.iam.web.util.KeycloakUtil;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final KeycloakUtil keycloakUtil;

    public GroupServiceImpl(KeycloakUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }

    @Override
    public void create(String name) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(name);
        keycloakUtil.getAdminInstance()
                .realm(keycloakUtil.realm)
                .groups()
                .add(group);
    }

    @Override
    public List<GroupRepresentation> findAll() {
        return keycloakUtil.getAdminInstance()
                .realm(keycloakUtil.realm)
                .groups()
                .groups();
    }
}
