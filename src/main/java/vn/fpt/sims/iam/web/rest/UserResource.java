package vn.fpt.sims.iam.web.rest;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.sims.iam.constant.EntitiesConstant;
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
 * REST controller for managing {@link vn.fpt.sims.iam User}.
 */
@RestController
@RequestMapping("/api/" + EntitiesConstant.USER)
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = EntitiesConstant.USER;

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final RoleService roleService;

    private final UserService userService;

    public UserResource(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    /**
     * {@code POST  } : Create a new User.
     *
     * @param userRequest of the User to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new User, or with status {@code 400 (Bad Request)} if the User has already an ID.
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserRequest userRequest) throws URISyntaxException {
        log.debug("REST request to create User: {}", userRequest);
        try (Response response = userService.create(userRequest)) {
            if (response.getStatus() != 201)
                throw new BadRequestIamException(ErrorsEnum.USER_NOT_CREATED);
            return ResponseEntity
                    .created(new URI("/" + ENTITY_NAME + "/" + userRequest.getUsername()))
                    .headers(HeaderUtil.createEntityCreationIam(applicationName, true, EntitiesConstant.USER, userRequest.getUsername()))
                    .body(response.getEntity());
        }
    }

    /**
     * {@code GET  } : get all the User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping
    public ResponseEntity<List<UserRepresentation>> getAll() {
        log.debug("REST request to get list of Users");
        List<UserRepresentation> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    /**
     * {@code GET  /:username} : get the "username" User.
     *
     * @param username of the User to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<UserRepresentation>> getById(@PathVariable String username) {
        log.debug("REST request to get User by ID: {}", username);
        List<UserRepresentation> roleRepresentation = userService.findByUsername(username);
        return ResponseEntity.ok().body(roleRepresentation);
    }

    /**
     * {@code GET  /:id} : get the "id" User.
     *
     * @param id of the User to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserRepresentation> getById(@PathVariable UUID id) {
        log.debug("REST request to get User by ID: {}", id);
        UserRepresentation roleRepresentation = userService.findById(id);
        return ResponseEntity.ok().body(roleRepresentation);
    }

    /**
     * {@code GET  /:id/group/:groupId} : search with "username" of the User.
     *
     * @param id      of the User to retrieve.
     * @param groupId of the Group to retrieve.
     * @return the {@link ResponseEntity} with status {@code 202 (accepted)}, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/{id}/groups/{groupId}")
    public ResponseEntity<List<UserRepresentation>> assignToGroup(@PathVariable UUID id,
                                                                  @PathVariable UUID groupId) {
        log.debug("REST request to assign to group for User by ID: {}", id);
        userService.assignToGroup(id, groupId);
        return ResponseEntity.accepted().build();
    }

    /**
     * {@code GET  /:id/roles/:roleName} : search with "username" of the User.
     *
     * @param id       of the User to retrieve.
     * @param roleName of the Role to retrieve.
     * @return the {@link ResponseEntity} with status {@code 202 (accepted)} or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/{id}/roles/{roleName}")
    public ResponseEntity<Void> assignRole(@PathVariable UUID id,
                                           @PathVariable String roleName) {
        log.debug("REST request to assign Role for User by ID: {}", id);
        RoleRepresentation role = roleService.findByName(roleName);
        userService.assignRole(id, role);
        return ResponseEntity.accepted().build();
    }
}
