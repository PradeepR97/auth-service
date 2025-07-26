package com.coffeespace.authservice.service;

import com.coffeespace.authservice.dto.OtpRequest;
import com.coffeespace.authservice.dto.OtpVerifyRequest;

public interface AuthService {
    String sendOtp(OtpRequest request);
    boolean verifyOtp(OtpVerifyRequest request);
}
