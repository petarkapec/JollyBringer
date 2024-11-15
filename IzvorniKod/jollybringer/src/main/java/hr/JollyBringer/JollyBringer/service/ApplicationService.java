package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<ApplicationRequest> listAll();


    ApplicationRequest fetch(long userId);
    // Note: verb "fetch" in method name is typically used when identified object is expected


    ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest);


    Optional<ApplicationRequest> findById(long userId);



    ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequest);

    ApplicationRequest deleteApplicationRequest(long userId);
}
