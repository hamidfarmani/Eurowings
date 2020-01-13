package com.eurowings.newsletter.controller;

import com.eurowings.newsletter.model.Subscription;
import com.eurowings.newsletter.model.User;
import com.eurowings.newsletter.service.SubscriptionService;
import com.eurowings.newsletter.service.UserService;
import com.eurowings.newsletter.util.enums.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @PostMapping("/subscriptions/{username}/subscribe")
    public ResponseEntity<Object> createSubscription(@Valid @PathVariable String username) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Subscription subscription = new Subscription();
        subscription.setUser(user.get());
        subscription.setActionType(SubscriptionType.SUBSCRIBED);
        subscription.setCreationDate(new Date());
        Subscription savedSubscription = subscriptionService.saveSubscription(subscription);
        if(savedSubscription == null){
            return ResponseEntity.badRequest().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSubscription.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/subscriptions/{username}/unsubscribe")
    public ResponseEntity<Object> creatUnsubscription(@Valid @PathVariable String username) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Subscription subscription = new Subscription();
        subscription.setUser(user.get());
        subscription.setActionType(SubscriptionType.UNSUBSCRIBED);
        subscription.setCreationDate(new Date());
        Subscription savedSubscription = subscriptionService.saveSubscription(subscription);
        if(savedSubscription == null){
            return ResponseEntity.badRequest().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSubscription.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/subscriptions/{username}/between/{start}/{end}")
    public ResponseEntity<Object> retrieveSubscriptionOfUserBetweenDate(@Valid @PathVariable String username,
                                                             @PathVariable String start,
                                                             @PathVariable String end) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(start);

            if(end.equals("")){
                endDate = new Date();
            }else{
                endDate = format.parse(end);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Optional<Subscription> subscription = subscriptionService.retrieveLastSubscriptionOfUser(user.get(), startDate, endDate);
        if(subscription.isPresent()){
            return new ResponseEntity<Object>(subscription.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subscriptions/{username}/before/{date}")
    public ResponseEntity<Object> retrieveSubscriptionOfUserBeforeDate(@Valid @PathVariable String username,
                                                                        @PathVariable String date) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date endDate = null;
        try {
            endDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Optional<Subscription> subscription = subscriptionService.retrieveLastSubscriptionOfUser(user.get(), new Date(Long.MIN_VALUE), endDate);
        if(subscription.isPresent()){
            return new ResponseEntity<Object>(subscription.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subscriptions/{username}/after/{date}")
    public ResponseEntity<Object> retrieveSubscriptionOfUserAfterDate(@Valid @PathVariable String username,
                                                                        @PathVariable String date) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate = null;
        try {
            startDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        Optional<Subscription> subscription = subscriptionService.retrieveLastSubscriptionOfUser(user.get(), startDate, new Date());
        if(subscription.isPresent()){
            return new ResponseEntity<Object>(subscription.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/subscriptions/{username}")
    public ResponseEntity<Object> retrieveSubscriptionsOfUser(@Valid @PathVariable String username) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        List<Subscription> subscriptions = subscriptionService.retrieveSubscriptionsOfUser(user.get());
        return new ResponseEntity<Object>(subscriptions, HttpStatus.OK);
    }


    @GetMapping("/subscriptions/{username}/status")
    public ResponseEntity<Object> shouldRecieveNewsletter(@Valid @PathVariable String username) {
        Optional<User> user = userService.retrieveUserByUsername(username);
        if(!user.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Optional<Subscription> subscriptions = subscriptionService.retrieveSubscriptionStatusOfUser(user.get());
        if(subscriptions.isPresent() && subscriptions.get().getActionType().equals(SubscriptionType.SUBSCRIBED)){
            return  new ResponseEntity<Object>("true", HttpStatus.OK);
        }else{
            return  new ResponseEntity<Object>("false", HttpStatus.OK);
        }
    }


    @GetMapping("/subscriptions/users/between/{start}/{end}")
    public ResponseEntity<Object> subscribedUserBetween(@Valid @PathVariable String start,
                                                        @PathVariable String end) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(start);
            endDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        List subscriptions = subscriptionService.retrieveSubscribedUserByCreationDate(startDate, endDate);
        return  new ResponseEntity<Object>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/users/after/{start}")
    public ResponseEntity<Object> subscribedUserAfter(@Valid @PathVariable String start) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date startDate = null;
        try {
            startDate = format.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        List subscriptions = subscriptionService.retrieveSubscribedUserByCreationDate(startDate, new Date());
        return  new ResponseEntity<Object>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/users/before/{end}")
    public ResponseEntity<Object> subscribedUserBefore(@Valid @PathVariable String end) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date endDate = null;
        try {
            endDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        List subscriptions = subscriptionService.retrieveSubscribedUserByCreationDate(new Date(Long.MIN_VALUE), endDate);
        return  new ResponseEntity<Object>(subscriptions, HttpStatus.OK);
    }



}
