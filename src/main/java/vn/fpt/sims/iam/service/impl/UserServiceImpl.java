package vn.fpt.sims.iam.service.impl;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.UserService;
import vn.fpt.sims.iam.service.dto.UserRequest;
import vn.fpt.sims.iam.web.util.KeycloakUtil;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final KeycloakUtil keycloakUtil;

    public UserServiceImpl(KeycloakUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }

    @Override
    public Response create(UserRequest newUser) {
        CredentialRepresentation credential = preparePasswordRepresentation(newUser.getPassword());
        UserRepresentation userRep = prepareUserRepresentation(newUser, credential);
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .create(userRep);
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    private UserRepresentation prepareUserRepresentation(UserRequest user, CredentialRepresentation credential) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getUsername());
        newUser.setCredentials(List.of(credential));
        newUser.setEnabled(true);
        return newUser;
    }

    @Override
    public List<UserRepresentation> findAll() {
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .list();
    }

    @Override
    public UserRepresentation findById(UUID id) {
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .get(id.toString())
                .toRepresentation();
    }

    @Override
    public List<UserRepresentation> findByUsername(String username) {
        return keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .search(username);
    }

    @Override
    public void assignToGroup(UUID id, UUID groupId) {
        keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .get(id.toString())
                .joinGroup(groupId.toString());
    }

    @Override
    public void assignRole(UUID id, RoleRepresentation roleRepresentation) {
        keycloakUtil
                .getAdminInstance()
                .realm(keycloakUtil.realm)
                .users()
                .get(id.toString())
                .roles()
                .realmLevel()
                .add(List.of(roleRepresentation));
    }
}
