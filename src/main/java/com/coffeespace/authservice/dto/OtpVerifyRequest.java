package com.coffeespace.authservice.dto;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String contactNumber;
    private String otp;
}
