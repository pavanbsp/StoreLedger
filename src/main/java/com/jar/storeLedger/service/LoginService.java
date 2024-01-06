package com.jar.storeLedger.service;

import com.jar.storeLedger.config.ApplicationProperties;
import com.jar.storeLedger.config.CustomAuthentication;
import com.jar.storeLedger.constants.Role;
import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.entity.User;
import com.jar.storeLedger.entity.repository.KiranaStoreRepository;
import com.jar.storeLedger.entity.repository.UserRepository;
import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.exception.ErrorCode;
import com.jar.storeLedger.model.LoginRequest;
import com.jar.storeLedger.model.LoginResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

    ApplicationProperties applicationProperties;
    UserRepository userRepository;
    KiranaStoreRepository kiranaStoreRepository;

    public void createNewOtp(String mobile) throws ApiException {
        checkIfKiranaStoreExists(mobile);
        User user = createOrGetUser(mobile);
        String newOtp = getNewOtp();
        user.setOtp(newOtp);
        userRepository.save(user);
    }

    public LoginResponse userLogin(LoginRequest request) throws ApiException {
        checkIfKiranaStoreExists(request.getMobile());
        User user = createOrGetUser(request.getMobile());
        if (!Objects.equals(user.getOtp(), request.getOtp())) {
            throw new ApiException("Invalid OTP", ErrorCode.BAD_REQUEST);
        }
        setAuthentication(user);

        LoginResponse response = new LoginResponse();
        response.setMobile(request.getMobile());
        if (user.getRole() == Role.STORE_USER) {
            KiranaStore kiranaStore = kiranaStoreRepository.select("mobile", request.getMobile());
            response.setKiranaStoreId(kiranaStore.getId());
        }
        return response;
    }

    protected void setAuthentication(User user) {
        CustomAuthentication authentication = new CustomAuthentication(user.getMobile(), null, Collections.singleton(convert(user.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static SimpleGrantedAuthority convert(Role role) {
        return new SimpleGrantedAuthority(role.name());
    }

    private void checkIfKiranaStoreExists(String mobile) throws ApiException {
        if (isAdmin(mobile)) {
            return;
        }
        KiranaStore kiranaStore = kiranaStoreRepository.select("mobile", mobile);
        if (kiranaStore == null) {
            throw new ApiException("No kirana store exists with the mobile number", ErrorCode.NOT_FOUND);
        }
    }

    private User createOrGetUser(String mobile) {
        User user = userRepository.select(mobile);
        if (user == null) {
            user = new User();
            user.setMobile(mobile);
            if (isAdmin(mobile)) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.STORE_USER);
            }
        }
        return user;
    }

    private boolean isAdmin(String mobile) {
        return Objects.equals(applicationProperties.getAdminMobile(), mobile);
    }

    private String getNewOtp() {
        return ThreadLocalRandom.current().nextInt(1000, 9999) + "";
    }

}
