package com.eurowings.newsletter.service;

import com.eurowings.newsletter.model.Subscription;
import com.eurowings.newsletter.model.User;
import com.eurowings.newsletter.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription saveSubscription(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }

    public Optional<Subscription> retrieveLastSubscriptionOfUser(User user, Date start, Date stop){
        return subscriptionRepository.findTopByUserAndCreationDateBetweenOrderByCreationDateDesc(user, start, stop);
    }

    public List<Subscription> retrieveSubscriptionsOfUser(User user){
        return subscriptionRepository.findByUser(user);
    }

    public Optional<Subscription> retrieveSubscriptionStatusOfUser(User user) {
        return subscriptionRepository.findTopByUserOrderByCreationDateDesc(user);
    }

    public List retrieveSubscribedUserByCreationDate(Date start, Date stop) {
        return subscriptionRepository.findDistinctUsersByCreationDate(start, stop);
    }
}
