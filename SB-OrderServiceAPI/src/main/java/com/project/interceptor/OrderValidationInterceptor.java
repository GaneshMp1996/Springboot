package com.project.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class OrderValidationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(OrderValidationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/orders") && "POST".equalsIgnoreCase(request.getMethod())) {
            String idempotencyKey = request.getHeader("Idempotency-Key");

            if (idempotencyKey == null || idempotencyKey.isEmpty()) {
                logger.error("Interceptor: Missing Idempotency-Key for business validation");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Idempotency-Key is required");
                return false; // stop request from reaching controller
            }
        }
        return true; // pass validation
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        logger.info("Interceptor POST: controller executed for URI={}", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        logger.info("Interceptor AFTER COMPLETION: status={}", response.getStatus());
    }
}
