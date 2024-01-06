package com.jar.storeLedger.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotNull(message = "Please enter mobile number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String mobile;
    @NotNull(message = "Please enter OTP")
    @Size(min = 4, max = 4, message = "Invalid OTP")
    private String otp;

}
