package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ApplicationRepository;
import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Participant;
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
    public ApplicationRequest fetch(long appId) {
        return findById(appId).orElseThrow(
                () -> new EntityMissingException(ApplicationRequest.class, appId)
        );
    }

    @Override
    public ApplicationRequest createApplicationRequest(ApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest, "ApplicationRequest object must be given");
        if (appRepo.countByUser(applicationRequest.getUser()) > 0)
            throw new RequestDeniedException(
                    "Request with userId" + applicationRequest.getUser().getId() + " already exists"
            );
        return appRepo.save(applicationRequest);
    }

    @Override
    public Optional<ApplicationRequest> findByUserId(long userId) {

        return appRepo.findByUserId(userId);
    }

    @Override
    public Optional<ApplicationRequest> findById(long userId) {
        return appRepo.findById(userId);
    }

    @Override
    public ApplicationRequest updateApplicationRequest(ApplicationRequest applicationRequest) {
        Assert.notNull(applicationRequest, "ApplicationRequest object must be given");
        Participant user = applicationRequest.getUser();
        if (!appRepo.existsByUser(user))
            throw new EntityMissingException(ApplicationRequest.class, user);
        return appRepo.save(applicationRequest);
    }

    @Override
    public ApplicationRequest deleteApplicationRequest(long appId) {
        ApplicationRequest applicationRequest = fetch(appId);
        appRepo.delete(applicationRequest);
        return applicationRequest;
    }


}
