package com.coffeespace.authservice.service.impl;

import com.coffeespace.authservice.dto.OtpRequest;
import com.coffeespace.authservice.dto.OtpVerifyRequest;
import com.coffeespace.authservice.entity.OtpVerification;
import com.coffeespace.authservice.repository.OtpVerificationRepository;
import com.coffeespace.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final OtpVerificationRepository otpRepo;

    @Override
    public String sendOtp(OtpRequest request) {
        String otp = String.valueOf(new Random().nextInt(9000) + 1000);
        OtpVerification otpVerification = OtpVerification.builder()
                .otp(otp)
                .contactNumber(request.getContactNumber())
                .otpGeneratedTime(LocalDateTime.now())
                .profileVerified(false)
                .build();

        otpRepo.save(otpVerification);

        // For now, mock SMS
        System.out.println("OTP sent to " + request.getContactNumber() + ": " + otp);
        return "OTP sent (mock).";
    }

    @Override
    public boolean verifyOtp(OtpVerifyRequest request) {
        return otpRepo.findTopByContactNumberOrderByOtpGeneratedTimeDesc(request.getContactNumber())
                .filter(o -> o.getOtp().equals(request.getOtp()))
                .map(o -> {
                    o.setProfileVerified(true);
                    otpRepo.save(o);
                    return true;
                }).orElse(false);
    }
}
