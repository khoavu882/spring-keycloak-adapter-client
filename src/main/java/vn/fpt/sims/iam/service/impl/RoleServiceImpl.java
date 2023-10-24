package vn.fpt.sims.iam.service.impl;

import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.RoleService;
import vn.fpt.sims.iam.web.util.KeycloakUtil;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final KeycloakUtil keycloakUtil;

    public RoleServiceImpl(KeycloakUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }

    @Override
    public void create(String name) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(name);
        keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .roles()
                .create(role);
    }

    @Override
    public List<RoleRepresentation> findAll() {
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .roles()
                .list();
    }

    @Override
    public RoleRepresentation findByName(String roleName) {
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .roles().get(roleName)
                .toRepresentation();
    }
}
