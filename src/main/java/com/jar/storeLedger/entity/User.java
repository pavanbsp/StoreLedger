package com.jar.storeLedger.entity;

import com.jar.storeLedger.constants.Role;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    private String mobile;
    private String otp;
    @Enumerated(EnumType.STRING)
    private Role role;

}
