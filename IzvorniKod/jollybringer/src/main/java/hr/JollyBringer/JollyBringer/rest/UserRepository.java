package hr.JollyBringer.JollyBringer.rest;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.JollyBringer.JollyBringer.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
