package com.lalabrand.ecommerce.security.password_reset;

import com.lalabrand.ecommerce.utils.CommonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @MutationMapping(name = "sendResetPasswordTokenOnEmail")
    public CommonResponse sendResetPasswordOnEmail(@Argument @Valid @Email String email) {
        boolean success = passwordResetService.sendPasswordResetTokenByEmail(email);
        String message = success ? "Password reset token sent successfully" : "Failed to send reset password on email";

        return CommonResponse.builder()
                .success(success)
                .message(message)
                .build();
    }

    @MutationMapping("resetPasswordByToken")
    public CommonResponse resetPasswordByToken(@Argument(name = "passwordResetInput")
                                                   @Valid PasswordResetRequest passwordResetRequest) {
        boolean success = passwordResetService.resetPasswordForUser(passwordResetRequest);
        String message = success ? "Password reset successfully" : "Failed to reset password";

        return CommonResponse.builder()
                .success(success)
                .message(message)
                .build();
    }
}
