package com.ifeed.mapper;

import com.ifeed.model.User;
import com.ifeed.model.dto.UserDTO;
import org.springframework.stereotype.Component;


/**
 * Created by Iulia-Anamaria Pascu on 2/11/2016.
 */
@Component
public class UserMapper extends CollectionMapper<User, UserDTO> {

    @Override
    public UserDTO map(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setUserName(entity.getUserName());

        dto.setCreatedTimestamp(entity.getCreatedTimestamp());
        dto.setLastLogin(entity.getLastLogin());

        return dto;
    }

    @Override
    public void map(UserDTO dto, User entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setEmail(dto.getEmail());
        entity.setUserName(dto.getUserName());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
    }
}

