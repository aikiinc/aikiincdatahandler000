package com.aikiinc.test.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(final String username) {
	this.username = username;
    }

    @Override
    public String toString() {
	return "User [id=" + id + ", username=" + username + "]";
    }
}
