package vn.fpt.sims.iam.web.util;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import vn.fpt.sims.iam.service.dto.UserRequest;

public class KeycloakUtil {

    @Value("${keycloak.auth-server-url}")
    public String authServerUrl;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.resource}")
    public String resource;
    @Value("${keycloak.credentials.secret}")
    public String secret;
    @Value("${keycloak.credentials.login-app-id}")
    public String loginAppId;
    @Value("${keycloak.credentials.login-app-secret}")
    public String loginAppSecret;

    public Keycloak getAdminInstance() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(resource)
                .clientSecret(secret)
                .build();
    }

    public Keycloak getInstance(UserRequest request) {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(resource)
                .clientSecret(secret)
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }
}
