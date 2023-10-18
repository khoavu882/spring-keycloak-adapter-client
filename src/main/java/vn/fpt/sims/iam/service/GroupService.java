package vn.fpt.sims.iam.service;

import org.keycloak.representations.idm.GroupRepresentation;

import java.util.List;

public interface GroupService {

    void create(String name);

    List<GroupRepresentation> findAll();
}
