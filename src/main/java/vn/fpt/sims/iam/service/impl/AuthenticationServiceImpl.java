package vn.fpt.sims.iam.service.impl;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.AuthenticationService;
import vn.fpt.sims.iam.service.UserService;
import vn.fpt.sims.iam.service.dto.UserRequest;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final Keycloak keycloak;

    private final KeycloakSpringBootProperties keycloakProp;

    public AuthenticationServiceImpl(Keycloak keycloak, KeycloakSpringBootProperties keycloakProp) {
        this.keycloak = keycloak;
        this.keycloakProp = keycloakProp;
    }

    @Override
    public AccessTokenResponse login(UserRequest request) {
        Keycloak newInstance = KeycloakBuilder.builder()
                .serverUrl(keycloakProp.getAuthServerUrl())
                .realm(keycloakProp.getRealm())
                .clientId(keycloakProp.getCredentials().get("login-app-id").toString())
                .clientSecret(keycloakProp.getCredentials().get("login-app-secret").toString())
                .username(request.getUsername())
                .password(request.getPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();

        return newInstance
                .tokenManager()
                .getAccessToken();
    }

    @Override
    public void logout(String accessToken) {
        keycloak
                .tokenManager()
                .getAccessToken();
    }
}
