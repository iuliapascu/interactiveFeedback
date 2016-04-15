package com.ifeed.model.dto;


public class UserEditDTO extends UserDTO {
    private String password;

    public UserEditDTO() {

    }

    public UserEditDTO(UserDTO userDTO) {
        super(userDTO);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
