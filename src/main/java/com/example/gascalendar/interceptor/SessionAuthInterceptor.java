package com.example.gascalendar.interceptor;

import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.enums.SessionKeys;
import com.example.gascalendar.service.UserService;
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
public class SessionAuthInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public SessionAuthInterceptor(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(SessionKeys.USER.toString());

        if (sessionUser instanceof UserResponse) {
            userService.refreshLastActive(((UserResponse) sessionUser).getId());
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
                response.getWriter(),
                new ApiResponse<>(false, "Authentication required", null)
        );
        return false;
    }
}
