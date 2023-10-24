package vn.fpt.sims.iam.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.keycloak.representations.idm.RoleRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.sims.iam.constant.EntitiesConstant;
import vn.fpt.sims.iam.service.RoleService;
import vn.fpt.sims.iam.web.util.HeaderUtil;

/**
 * REST controller for managing {@link vn.fpt.sims.iam Role}.
 */
@RestController
@RequestMapping("/api/" + EntitiesConstant.ROLE)
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = EntitiesConstant.ROLE;

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * {@code POST  } : Create a new Role.
     *
     * @param name of the Role to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the Role, or with status {@code 400 (Bad Request)} if the Role has already an ID.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestParam String name) throws URISyntaxException {
        log.debug("REST request to create Role : {}", name);
        roleService.create(name);
        return ResponseEntity
                .created(new URI("/" + ENTITY_NAME))
                .headers(HeaderUtil.createEntityCreationIam(applicationName, true, EntitiesConstant.ROLE, name))
                .body(name);
    }

    /**
     * {@code GET  } : get all the Role.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributes in body.
     */
    @GetMapping
    public ResponseEntity<List<RoleRepresentation>> getAll() {
        log.debug("REST request to get list of Roles");
        List<RoleRepresentation> roles = roleService.findAll();
        return ResponseEntity.ok().body(roles);
    }

    /**
     * {@code GET  /:name} : get the "name" Role.
     *
     * @param name the name of the Role to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{name}")
    public ResponseEntity<RoleRepresentation> getByName(@PathVariable String name) {
        log.debug("REST request to get Role by name : {}", name);
        RoleRepresentation roleRepresentation = roleService.findByName(name);
        return ResponseEntity.ok().body(roleRepresentation);
    }
}
