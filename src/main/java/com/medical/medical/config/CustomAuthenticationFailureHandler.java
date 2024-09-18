package com.medical.medical.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Erreur de connexion : " + exception.getMessage());
        // Vous pouvez également personnaliser la réponse ici, si nécessaire
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }
}
