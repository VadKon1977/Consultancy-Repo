package com.consultancy.project.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Component
public class TracingFilter extends OncePerRequestFilter {

    private final String XtracingId = "X-Trace-Id";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
                String traceId = UUID.randomUUID().toString();
                String dateTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                MDC.put(Constants.TRACE_ID_KEY, traceId);
                MDC.put(Constants.REQUEST_TIME, dateTimeStamp);
                response.addHeader(XtracingId, traceId);
                filterChain.doFilter(request, response);
        } finally {
            MDC.remove(Constants.TRACE_ID_KEY);
        }
    }
}
