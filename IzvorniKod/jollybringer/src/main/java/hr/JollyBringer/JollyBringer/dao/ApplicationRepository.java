package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationRequest, Long> {
    double countByUserId(long attr0);
}
