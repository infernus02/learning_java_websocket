package com.project.webchatbe.service.imp;

import com.project.webchatbe.dto.forgot_pass.request.ResetPassRequest;
import com.project.webchatbe.entity.TokenResetPass;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.exception.AppException;
import com.project.webchatbe.exception.ErrorCode;
import com.project.webchatbe.repository.ITokenResetPassRepository;
import com.project.webchatbe.repository.IUserRepository;
import com.project.webchatbe.service.interfaces.IEmailService;
import com.project.webchatbe.service.interfaces.ITokenResetPassService;
import com.project.webchatbe.service.interfaces.IUserService;
import com.project.webchatbe.util.UtilDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class TokenResetPassService implements ITokenResetPassService {
    @Autowired
    ITokenResetPassRepository tokenResetPassRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserService userService;

    @Autowired
    IEmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.reset-password-url}")
    String linkResetPass;

    @Override
    public void forgotPassword(String email) {
        User user = userService.findByEmail(email);

        //---xóa token cũ của user
        tokenResetPassRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();
        TokenResetPass tokenResetPass = TokenResetPass.builder()
                .token(token)
                .user(user)
                .expiredAt(UtilDateTime.getLocalDateTimeVN().plusMinutes(15))
                .build();

        tokenResetPassRepository.save(tokenResetPass);

        emailService.sendEmailResetPass(email, linkResetPass + token);
    }

    @Override
    public void resetPassword(ResetPassRequest request) {
        if(request.getNewPassword() == null || request.getNewPassword().isEmpty())
            throw new AppException(ErrorCode.PASSWORD_NOT_NULL);

        TokenResetPass tokenResetPass = tokenResetPassRepository.findByToken(request.getToken()).orElseThrow(
                        () -> new AppException(ErrorCode.TOKEN_RESET_PASS_NOT_FOUND)
                );

        if(tokenResetPass.getExpiredAt().isBefore(UtilDateTime.getLocalDateTimeVN()))
            throw new AppException(ErrorCode.TOKEN_EXPIRED);

        User user = tokenResetPass.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenResetPassRepository.delete(tokenResetPass);
    }

    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(cron = "0 */1 * * * *")

    @Override
    public void cleanTokenSchedule() {
        List<TokenResetPass> tokenResetPasses = tokenResetPassRepository.findAll();
        for(var it: tokenResetPasses){
            if(it.getExpiredAt().isBefore(UtilDateTime.getLocalDateTimeVN())) {
                log.warn("delete token reset pass user: " + it.getUser().getUsername());
                tokenResetPassRepository.delete(it);
            }
        }
    }
}
