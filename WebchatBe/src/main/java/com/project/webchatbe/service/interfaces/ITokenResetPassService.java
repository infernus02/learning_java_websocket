package com.project.webchatbe.service.interfaces;

import com.project.webchatbe.dto.forgot_pass.request.ResetPassRequest;

public interface ITokenResetPassService {
    public void forgotPassword(String email);
    public void resetPassword(ResetPassRequest request);
    public void cleanTokenSchedule();
}
