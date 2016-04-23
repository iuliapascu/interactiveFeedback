package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;
import com.ifeed.model.enums.UserRole;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 50, name = "user_name")
    private String userName;

    /**
     * password hash
     */
    @Basic
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = true, length = 50)
    private String firstName;

    @Column(nullable = true, length = 50)
    private String lastName;

    @Column(nullable = false, name = "created_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;

    @Column(nullable = true, name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.createdTimestamp = new Date();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
