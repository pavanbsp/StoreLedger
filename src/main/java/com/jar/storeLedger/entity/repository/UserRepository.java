package com.jar.storeLedger.entity.repository;

import com.jar.storeLedger.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }

}