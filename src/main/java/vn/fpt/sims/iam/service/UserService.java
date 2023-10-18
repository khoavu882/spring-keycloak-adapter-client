package vn.fpt.sims.iam.service;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import vn.fpt.sims.iam.service.dto.UserRequest;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public interface UserService {

    Response create(UserRequest newUser);

    List<UserRepresentation> findAll();

    UserRepresentation findById(UUID id);

    List<UserRepresentation> findByUsername(String username);

    void assignToGroup(UUID id, UUID groupId);

    void assignRole(UUID id, RoleRepresentation roleRepresentation);

}
