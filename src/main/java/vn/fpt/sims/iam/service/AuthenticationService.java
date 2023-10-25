package vn.fpt.sims.iam.service;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import vn.fpt.sims.iam.service.dto.UserRequest;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public interface AuthenticationService {

    AccessTokenResponse login(UserRequest request);

    void logout(String accessToken);

}
