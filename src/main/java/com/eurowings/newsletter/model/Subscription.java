package com.eurowings.newsletter.model;

import com.eurowings.newsletter.util.enums.SubscriptionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "Information that each user posted.")
@Entity
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

    private Date creationDate;


    @Enumerated(EnumType.STRING)
    private SubscriptionType actionType;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Subscription(Long id, String description) {
        this.id = id;
        this.description = description;
    }


    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public SubscriptionType getActionType() {
        return actionType;
    }

    public void setActionType(SubscriptionType actionType) {
        this.actionType = actionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
