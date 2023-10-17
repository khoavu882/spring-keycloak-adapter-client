package vn.fpt.sims.iam.service;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {

    void create(String name);

    List<RoleRepresentation> findAll();

    RoleRepresentation findByName(String roleName);
}
