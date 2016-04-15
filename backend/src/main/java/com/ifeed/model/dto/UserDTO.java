package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by Iulia-Anamaria Pascu on 2/11/2016.
 */
public class UserDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String userName;

    @NotBlank
    private String email;

    private String firstName;
    private String lastName;

    private Date createdTimestamp;
    private Date lastLogin;

    public UserDTO() {

    }

    // copy constructor
    public UserDTO(UserDTO userDTO) {
        this.setId(userDTO.getId());
        this.setVersion(userDTO.getVersion());

        this.setEmail(userDTO.getEmail());
        this.setFirstName(userDTO.getFirstName());
        this.setLastName(userDTO.getLastName());
        this.setUserName(userDTO.getUserName());

        this.setCreatedTimestamp(userDTO.getCreatedTimestamp());
        this.setLastLogin(userDTO.getLastLogin());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
