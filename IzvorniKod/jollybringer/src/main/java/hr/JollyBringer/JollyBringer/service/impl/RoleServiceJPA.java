package hr.JollyBringer.JollyBringer.service.impl;


import hr.JollyBringer.JollyBringer.dao.RoleRepository;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import hr.JollyBringer.JollyBringer.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceJPA implements RoleService {

    public final RoleRepository roleRepo;

    public RoleServiceJPA(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }


    @Override
    public List<Role> listAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role fetch(long roleId) {
        return findById(roleId).orElseThrow(
                () -> new EntityMissingException(Role.class, roleId)
        );
    }

    @Override
    public Role createRole(Role role) {
        validate(role);
        if (roleRepo.countByName(role.getName()) > 0)
            throw new RequestDeniedException(
                    "Role with name" + role.getName() + " already exists"
            );
        return roleRepo.save(role);
    }

    @Override
    public Optional<Role> findById(long roleId) {
        return roleRepo.findById(roleId);
    }

    @Override
    public Optional<Role> findByName(String name) {
        Assert.notNull(name, "Name must be given");
        return roleRepo.findByName(name);
    }

    @Override
    public Role updateRole(Role role) {
        validate(role);
        Long roleId = role.getId();
        if (!roleRepo.existsById(roleId))
            throw new EntityMissingException(Role.class, roleId);
        if (roleRepo.existsByNameAndIdNot(role.getName(), roleId))
            throw new RequestDeniedException(
                    "Role with name" + role.getName() + " already exists"
            );
        return roleRepo.save(role);
    }

    @Override
    public Role deleteRole(long roleId) {
        Role role = fetch(roleId);
        roleRepo.delete(role);
        return role;
    }

    private void validate(Role role) {
        Assert.notNull(role, "role object must be given");

    }
}
