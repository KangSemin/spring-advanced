package org.example.expert.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminLoggingAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(org.example.expert.domain.common.annotation.AdminLog)")
    public Object logAdminAction(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        // 요청 시각
        String requestTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        // 요청 URL
        String requestUrl = request.getRequestURL().toString();
        
        // 사용자 ID 추출
        Long userId = (Long) request.getAttribute("userId");
        
        // 요청 본문 추출
        String requestBody = "없음";
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && !(args[0] instanceof AuthUser)) {
            requestBody = objectMapper.writeValueAsString(args[0]);
        }

        // 메서드 실행 전 로깅
        log.info("[Admin API 요청] 시각: {}, URL: {}, 사용자ID: {}, 요청본문: {}", 
                requestTime, requestUrl, userId, requestBody);

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 응답 본문 변환
        String responseBody = result != null ? objectMapper.writeValueAsString(result) : "없음";

        // 메서드 실행 후 로깅
        log.info("[Admin API 응답] 시각: {}, URL: {}, 사용자ID: {}, 응답본문: {}", 
                requestTime, requestUrl, userId, responseBody);

        return result;
    }
} 