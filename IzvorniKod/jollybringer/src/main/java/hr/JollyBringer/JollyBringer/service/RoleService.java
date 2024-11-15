package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface RoleService {

    List<Role> listAll();


    Role fetch(long roleId);
    // Note: verb "fetch" in method name is typically used when identified object is expected


    Role createRole(Role role);


    Optional<Role> findById(long roleId);


    Optional<Role> findByName(String name);


    Role updateRole(Role role);

    Role deleteRole(long roleId);


}
