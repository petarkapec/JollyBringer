package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;

import java.util.List;

public interface ActivityService {
    /**
     * Lists all Activities in the system.
     * @return a list with all Participants
     */
    List<Activity> listAll();
}
