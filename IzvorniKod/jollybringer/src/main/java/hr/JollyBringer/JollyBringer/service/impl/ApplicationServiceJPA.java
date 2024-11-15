package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ApplicationRepository;
import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.ApplicationService;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceJPA implements ApplicationService {

    public final ApplicationRepository appRepo;

    public ApplicationServiceJPA(ApplicationRepository appRepo) {
        this.appRepo = appRepo;
    }

    @Override
    public List<ApplicationRequest> listAll() {
        return appRepo.findAll();
    }

    @Override
    public ApplicationRequest fetch(long userId) {
        return findById(userId).orElseThrow(
                () -> new EntityMissingException(ApplicationRequest.class, userId)
        );
    }

    @Override
    public ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest, "ApplicationRequest object must be given");
        if (appRepo.countByUserId(applicationRequest.getUserId()) > 0)
            throw new RequestDeniedException(
                    "Request with userId" + applicationRequest.getUserId() + " already exists"
            );
        return appRepo.save(applicationRequest);
    }

    @Override
    public Optional<ApplicationRequest> findById(long userId) {
        return appRepo.findById(userId);
    }

    @Override
    public ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest, "ApplicationRequest object must be given");
        Long userId = applicationRequest.getUserId();
        if (!appRepo.existsById(userId))
            throw new EntityMissingException(Role.class, userId);
        return appRepo.save(applicationRequest);
    }

    @Override
    public ApplicationRequest deleteApplicationRequest(long userId) {
        ApplicationRequest applicationRequest = fetch(userId);
        appRepo.delete(applicationRequest);
        return applicationRequest;
    }
}
