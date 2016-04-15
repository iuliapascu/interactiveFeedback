package com.ifeed.repository;

import com.ifeed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Iulia-Anamaria Pascu on 2/11/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u from User as u where u.userName = :user_name")
    User findByUserName(@Param("user_name") String userName);
}
