package com.consultancy.project.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
public class TracingFilter extends OncePerRequestFilter {

    private final String XtracingId = "X-Trace-Id";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
                String traceId = UUID.randomUUID().toString();
                MDC.put(Constants.TRACE_ID_KEY, traceId);
                response.addHeader(XtracingId, traceId);
                filterChain.doFilter(request, response);
        } finally {
            MDC.remove(Constants.TRACE_ID_KEY);
        }
    }
}
