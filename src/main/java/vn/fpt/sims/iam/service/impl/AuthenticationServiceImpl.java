package vn.fpt.sims.iam.service.impl;

import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.fpt.sims.iam.service.AuthenticationService;
import vn.fpt.sims.iam.service.dto.UserRequest;
import vn.fpt.sims.iam.web.util.KeycloakUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final KeycloakUtil keycloakUtil;

    public AuthenticationServiceImpl(KeycloakUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }

    @Override
    public AccessTokenResponse login(UserRequest request) {
        return keycloakUtil.getInstance(request)
                .tokenManager()
                .getAccessToken();
    }

    @Override
    public void logout(String accessToken) {
        keycloakUtil.getAdminInstance()
                .tokenManager()
                .invalidate(accessToken);
    }
}
