package vn.fpt.sims.iam.web.rest;

import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.sims.iam.constant.EntitiesConstant;
import vn.fpt.sims.iam.service.AuthenticationService;
import vn.fpt.sims.iam.service.dto.UserRequest;

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
    public ResponseEntity<AccessTokenResponse> create(@RequestBody UserRequest userRequest) {
        log.debug("REST request to login with username: {}", userRequest.getUsername());
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
        log.debug("REST request to logout with AccessToken: {}", accessToken);
        authService.logout(accessToken);
        return ResponseEntity
                .ok()
                .build();
    }
}
