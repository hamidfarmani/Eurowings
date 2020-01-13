package com.eurowings.newsletter.model;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//@JsonFilter("userFilter")
@ApiModel(description = "This the user which have the most important role in this application.")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;


    @Size(min = 2, message = "Name should have at least two characters.")
    private String name;
    @Past
    @ApiModelProperty(notes = "Birthdate should be in the past of course!")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthdate;
    private String username;
    private String email;

    @JsonIgnore
    private String password;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;


    public User() {
    }

    public User(Long id, String name, Date birthdate, String username, String password) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
