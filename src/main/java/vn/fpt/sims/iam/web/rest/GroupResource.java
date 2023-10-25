package vn.fpt.sims.iam.web.rest;

import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.sims.iam.constant.EntitiesConstant;
import vn.fpt.sims.iam.service.GroupService;
import vn.fpt.sims.iam.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link vn.fpt.sims.iam Group}.
 */
@RestController
@RequestMapping("/api/" + EntitiesConstant.GROUP)
public class GroupResource {

    private final Logger log = LoggerFactory.getLogger(GroupResource.class);

    private static final String ENTITY_NAME = EntitiesConstant.GROUP;

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final GroupService groupService;

    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * {@code POST  } : Create a new Group.
     *
     * @param name of the Group to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeDTO, or with status {@code 400 (Bad Request)} if the attribute has already an ID.
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestParam String name) throws URISyntaxException {
        log.debug("REST request to create Group: {}", name);
        groupService.create(name);
        return ResponseEntity
                .created(new URI("/" + ENTITY_NAME))
                .headers(HeaderUtil.createEntityCreationIam(applicationName, true, EntitiesConstant.ROLE, name))
                .build();
    }

    /**
     * {@code GET  } : get all the Group.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributes in body.
     */
    @GetMapping
    public ResponseEntity<List<GroupRepresentation>> getAll() {
        log.debug("REST request to get list of Group");
        List<GroupRepresentation> groups = groupService.findAll();
        return ResponseEntity.ok().body(groups);
    }
}
