package vn.fpt.sims.iam.web.rest;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.sims.iam.constant.EntitiesConstant;
import vn.fpt.sims.iam.service.AuthenticationService;
import vn.fpt.sims.iam.service.RoleService;
import vn.fpt.sims.iam.service.UserService;
import vn.fpt.sims.iam.service.dto.UserRequest;
import vn.fpt.sims.iam.web.errors.BadRequestIamException;
import vn.fpt.sims.iam.web.errors.ErrorsEnum;
import vn.fpt.sims.iam.web.util.HeaderUtil;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing {@link vn.fpt.sims.iam Auth}.
 */
@RestController
@RequestMapping("/api/" + EntitiesConstant.AUTH)
public class AuthenticationResource {

    private final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);

    private static final String ENTITY_NAME = EntitiesConstant.AUTH;

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final AuthenticationService authService;

    public AuthenticationResource(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * {@code POST /login } : Login User.
     *
     * @param userRequest of the User to login.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the new AccessToken, or with status {@code 400 (Bad Request)} if the username or password has already invalid.
     */
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> create(@RequestBody UserRequest userRequest) throws URISyntaxException {
        log.debug("REST request to create user : {}", userRequest);
        AccessTokenResponse response = authService.login(userRequest);
        return ResponseEntity
                .ok()
                .body(response);
    }

    /**
     * {@code GET /logout } : Logout User.
     * @param accessToken of the User to logout.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 400 (Bad Request)} if the AccessToken has already invalid.
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        log.debug("REST request to logout with AccessToken");
        authService.logout(accessToken);
        return ResponseEntity
                .ok()
                .build();
    }
}
