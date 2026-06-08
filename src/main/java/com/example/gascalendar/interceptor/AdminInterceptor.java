package com.example.gascalendar.interceptor;

import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.enums.SessionKeys;
import com.example.gascalendar.entity.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    public AdminInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(SessionKeys.USER.toString());

        if (sessionUser instanceof UserResponse user) {
            if (user.getRole() == UserRole.ADMIN) {
                return true;
            }

            writeError(response, HttpServletResponse.SC_FORBIDDEN, "Admin access required");
            return false;
        }

        writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
        return false;
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
                response.getWriter(),
                new ApiResponse<>(false, message, null)
        );
    }
}
