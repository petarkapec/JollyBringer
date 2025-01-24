package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    double countByName(String name);

    boolean existsByNameAndIdNot(String name, Long roleId);
}
