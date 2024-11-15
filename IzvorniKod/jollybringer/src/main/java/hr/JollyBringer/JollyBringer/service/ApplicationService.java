package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Participant;
import jakarta.servlet.http.Part;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<ApplicationRequest> listAll();


    ApplicationRequest fetch(long appId);
    // Note: verb "fetch" in method name is typically used when identified object is expected


    ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest);



    Optional<ApplicationRequest> findByUserId(long userId);

    Optional<ApplicationRequest> findById(long appId);



    ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequest);

    ApplicationRequest deleteApplicationRequest(long appId);


}
