package com.jar.storeLedger.controller;

import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.model.LoginRequest;
import com.jar.storeLedger.model.LoginResponse;
import com.jar.storeLedger.service.LoginService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {

    LoginService loginService;

    /**
     * Endpoint to generate OTP for user login.
     *
     * @param mobile The mobile number for which OTP is to be generated. Must be a 10-digit number.
     * @throws ApiException If there is an error while generating the OTP.
     */
    @PutMapping("login/otp/{mobile}")
    public void generateOtp(@PathVariable("mobile") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number") String mobile) throws ApiException {
        loginService.createNewOtp(mobile);
    }

    /**
     * Endpoint for user login using OTP.
     *
     * @param request The login request containing user credentials.
     * @return A response containing required attributes.
     * @throws ApiException If there is an error during the login process.
     */
    @PostMapping("login/otp")
    public LoginResponse kiranaUserLogin(@RequestBody @Valid LoginRequest request) throws ApiException {
        return loginService.userLogin(request);
    }

}
